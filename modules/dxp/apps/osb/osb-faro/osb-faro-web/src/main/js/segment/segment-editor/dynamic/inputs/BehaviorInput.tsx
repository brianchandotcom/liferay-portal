import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import DateFilterConjunctionInput from './components/DateFilterConjunctionInput';
import Form from 'shared/components/form';
import OccurenceConjunctionInput from './components/OccurenceConjunctionInput';
import React from 'react';
import SelectEntityFromModal from './components/SelectEntityFromModal';
import {
	ACTIVITY_KEY,
	FunctionalOperators,
	RelationalOperators
} from '../utils/constants';
import {activityAssetsListColumns} from 'shared/util/table-columns';
import {AssetNames} from 'shared/util/constants';
import {COUNT, createOrderIOMap} from 'shared/util/pagination';
import {Criterion, ISegmentEditorCustomInputBase} from '../utils/types';
import {CustomValue} from 'shared/util/records';
import {
	EntityType,
	ReferencedObjectsContext
} from '../context/referencedObjects';
import {fromJS, Map} from 'immutable';
import {get} from 'lodash';
import {
	getFilterCriterionIMap,
	getIndexFromPropertyName,
	getPropertyValue,
	setPropertyValue
} from '../utils/custom-inputs';
import {isBoolean, isNil, isNull} from 'lodash';
import {Modal} from 'shared/types/Modal';
import {parseActivityKey} from '../utils/utils';

export const AssetItem: React.FC<{
	dataSourceAssetPK?: string;
	name: string;
}> = ({dataSourceAssetPK = '', name}) => (
	<div className='asset-display-root' title={dataSourceAssetPK}>
		<div className='asset-name text-truncate'>{name}</div>

		{!!dataSourceAssetPK && (
			<div
				data-tooltip
				data-tooltip-align='top'
				title={dataSourceAssetPK}
			>
				<div className='asset-url text-secondary text-truncate'>
					{dataSourceAssetPK}
				</div>
			</div>
		)}
	</div>
);

const ASSET_MODAL_CONFIG_MAP = {
	[AssetNames.CommentPosted]: {
		columns: [activityAssetsListColumns.commentCount],
		label: Liferay.Language.get('comments')
	},
	[AssetNames.DocumentDownloaded]: {
		columns: [activityAssetsListColumns.downloadCount],
		label: Liferay.Language.get('downloads')
	},
	[AssetNames.FormSubmitted]: {
		columns: [activityAssetsListColumns.submissionCount],
		label: Liferay.Language.get('submissions')
	}
};

type Asset = {
	dataSourceAssetPK: string;
	id: string;
	name: string;
};

type Touched = {
	asset: boolean;
	dateFilter: boolean;
	occurenceCount: boolean;
};

type Valid = {
	asset: boolean;
	dateFilter: boolean;
	occurenceCount: boolean;
};

interface IBehaviorInputProps extends ISegmentEditorCustomInputBase {
	channelId: string;
	close: Modal.close;
	open: Modal.open;
	touched: Touched;
	valid: Valid;
}

export class BehaviorInput extends React.Component<IBehaviorInputProps> {
	static contextType = ReferencedObjectsContext;

	_completedAnalytics = false;

	componentDidUpdate() {
		const {
			id,
			property: {entityName, type},
			valid: {asset, dateFilter, occurenceCount}
		} = this.props;

		this.validateAsset();

		const valid = asset && dateFilter && occurenceCount;

		if (!id && valid && !this._completedAnalytics) {
			this._completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	}

	@autobind
	assetsDataFn({delta, orderIOMap, page, query}) {
		const {
			channelId,
			groupId,
			property: {entityType, name}
		} = this.props;

		return API.activities.searchAssets({
			applicationId: entityType,
			channelId,
			cur: page,
			delta,
			eventId: name,
			groupId,
			orderIOMap,
			query
		});
	}

	createActivityKey(asset) {
		const {property} = this.props;

		return `${property.entityType}#${property.name}#${asset.id}`;
	}

	getAssetFromContext(): Asset | undefined {
		const {
			context: {referencedEntities}
		} = this;

		const id = this.getAssetId();

		const reference = referencedEntities.getIn([EntityType.Assets, id]);

		return reference && reference.toJS();
	}

	getAssetId() {
		const {value} = this.props;

		const activityKeyIndex = getIndexFromPropertyName(value, ACTIVITY_KEY);

		const activityKey = getPropertyValue(value, 'value', activityKeyIndex);

		const {id} = parseActivityKey(activityKey);

		return id;
	}

	getConjunctionDateFilterIMap(value) {
		const conjunctionDateFilterIndex = getIndexFromPropertyName(
			value,
			'day'
		);

		if (conjunctionDateFilterIndex >= 0) {
			return getFilterCriterionIMap(value, conjunctionDateFilterIndex);
		}
	}

	@autobind
	handleAssetSelect(items) {
		const {
			context: {addEntities, addEntity},
			props: {onChange, touched, valid, value}
		} = this;

		const asset = items.first();

		const propertyIndex = getIndexFromPropertyName(value, ACTIVITY_KEY);

		if (items.size === 1) {
			addEntity({entityType: EntityType.Assets, payload: Map(asset)});

			onChange({
				valid: {...valid, asset: true},
				value: setPropertyValue(
					value,
					'value',
					propertyIndex,
					this.createActivityKey(asset)
				)
			});
		} else {
			addEntities({
				entityType: EntityType.Assets,
				payload: items.map(Map).valueSeq().toArray()
			});

			onChange(
				items
					.valueSeq()
					.map(assetItem => ({
						touched,
						valid: {...valid, asset: true},
						value: setPropertyValue(
							value,
							'value',
							propertyIndex,
							this.createActivityKey(assetItem)
						)
					}))
					.toArray()
			);
		}
	}

	@autobind
	handleDateFilterConjunctionChange(criterion) {
		const {onChange, touched, valid, value} = this.props;

		onChange({
			touched: {...touched, dateFilter: criterion && criterion.touched},
			valid: {...valid, dateFilter: isNull(criterion) || criterion.valid},
			value: isNull(criterion)
				? value.deleteIn(['criterionGroup', 'items', 1])
				: value.mergeIn(
						['criterionGroup', 'items', 1],
						fromJS(criterion)
				  )
		});
	}

	@autobind
	handleOccurenceConjunctionChange({
		criterion,
		touched: occurenceCountTouched,
		valid: occurenceCountValid
	}: {
		criterion?: Criterion;
		touched?: boolean;
		valid?: boolean;
	}) {
		const {onChange, touched, valid, value: valueIMap} = this.props;

		let params: {touched?: Touched; valid?: Valid; value?: CustomValue} = {
			touched,
			valid
		};

		if (criterion?.operatorName) {
			params = {
				...params,
				value: valueIMap.mergeIn(
					['operator'],
					criterion.operatorName
				) as CustomValue
			};
		} else if (!isNil(criterion?.value)) {
			params = {
				...params,
				value: valueIMap.mergeIn(
					['value'],
					criterion.value
				) as CustomValue
			};
		}

		if (isBoolean(occurenceCountTouched)) {
			params = {
				...params,
				touched: {...touched, occurenceCount: occurenceCountTouched}
			};
		}

		if (isBoolean(occurenceCountValid)) {
			params = {
				...params,
				valid: {...valid, occurenceCount: occurenceCountValid}
			};
		}

		onChange(params);
	}

	invalidateAsset() {
		const {onChange, touched, valid} = this.props;

		onChange({
			touched: {...touched, asset: true},
			valid: {...valid, asset: false}
		});
	}

	validateAsset() {
		const {valid} = this.props;

		if (valid.asset && !this.getAssetFromContext()) {
			this.invalidateAsset();
		}
	}

	render() {
		const {
			displayValue,
			groupId,
			operatorRenderer: OperatorDropdown,
			property,
			touched,
			valid,
			value
		} = this.props;

		const {
			columns = [activityAssetsListColumns.viewCount],
			label = Liferay.Language.get('views')
		}: {columns?: any; label?: any} = get(
			ASSET_MODAL_CONFIG_MAP,
			property.name,
			{}
		);

		const conjunctionCriterion = (
			this.getConjunctionDateFilterIMap(value) ||
			Map({propertyName: 'day'})
		).toJS();

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{property.entityName}
					</Form.GroupItem>

					<OperatorDropdown />

					<Form.GroupItem className='display-value' label shrink>
						<b>{displayValue}</b>
					</Form.GroupItem>

					<SelectEntityFromModal
						columns={[
							activityAssetsListColumns.nameUrl,
							...columns
						]}
						dataSourceFn={this.assetsDataFn}
						entity={this.getAssetFromContext()}
						error={touched.asset && !valid.asset}
						groupId={groupId}
						initialOrderIOMap={createOrderIOMap(COUNT)}
						noResultsIcon='web-content'
						onSubmit={this.handleAssetSelect}
						orderByOptions={[
							{
								label,
								value: COUNT
							}
						]}
						renderEntity={asset => (
							<AssetItem {...asset} title={label} />
						)}
						title={property.label}
					/>
				</Form.Group>

				<Form.Group autoFit>
					<OccurenceConjunctionInput
						onChange={this.handleOccurenceConjunctionChange}
						operatorName={
							value.get('operator') as FunctionalOperators &
								RelationalOperators
						}
						touched={touched.occurenceCount}
						valid={valid.occurenceCount}
						value={value.get('value')}
					/>

					<DateFilterConjunctionInput
						conjunctionCriterion={conjunctionCriterion}
						onChange={this.handleDateFilterConjunctionChange}
					/>
				</Form.Group>
			</div>
		);
	}
}

export default BehaviorInput;
