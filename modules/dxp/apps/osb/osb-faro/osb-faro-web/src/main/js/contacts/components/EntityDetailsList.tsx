import autobind from 'autobind-decorator';
import Card from 'shared/components/Card';
import Checkbox from 'shared/components/Checkbox';
import getCN from 'classnames';
import Nav from 'shared/components/Nav';
import Promise from 'metal-promise';
import React from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import {detailsListColumns} from 'shared/util/table-columns';
import {isBlank} from 'shared/util/util';
import {pick, some} from 'lodash';
import {sub} from 'shared/util/lang';
import {withStatefulPagination} from 'shared/hoc';

const DETAIL_QUERY_OPTIONS = ['dataSourceName', 'name', 'sourceName', 'value'];

const SearchableEntityTableStateful = withStatefulPagination(
	SearchableEntityTable
);

interface IEntityDetailsListProps extends React.HTMLAttributes<HTMLElement> {
	demographicsIMap: any;
	groupId: string;
	timeZoneId?: string;
	title?: string;
}

export default class EntityDetailsList extends React.Component<IEntityDetailsListProps> {
	static defaultProps = {
		title: Liferay.Language.get('properties')
	};

	state = {
		hideBlanks: false
	};

	_detailsData: any;
	_knownCount: any;

	constructor(props) {
		super(props);

		this.getDetailsData();
		this.getKnownCount();
	}

	@autobind
	filterDetails({hideBlanks, query = ''}) {
		const items = this._detailsData.filter(rowData => {
			if (hideBlanks && isBlank(rowData.value)) {
				return false;
			}

			return some(pick(rowData, DETAIL_QUERY_OPTIONS), item =>
				item
					? item
							.toString()
							.toLowerCase()
							.includes(query.toLowerCase())
					: ''
			);
		});

		return Promise.resolve({
			items,
			total: items.length
		});
	}

	getColumns() {
		const {groupId, timeZoneId} = this.props;

		return [
			detailsListColumns.name,
			detailsListColumns.sourceName,
			detailsListColumns.getDataSourceName(groupId),
			detailsListColumns.getDateModified(timeZoneId)
		];
	}

	getDetailsData() {
		const {demographicsIMap} = this.props;

		this._detailsData = demographicsIMap
			.map(values => {
				const fieldValue = values.get(0);

				return {
					dataSourceId: fieldValue.get('dataSourceId'),
					dataSourceName: fieldValue.get('dataSourceName'),
					dateModified: fieldValue.get('dateModified'),
					name: fieldValue.get('name'),
					sourceName: fieldValue.get('sourceName'),
					value: values
						.map(fieldMapping => fieldMapping.get('value'))
						.join(', ')
				};
			})
			.valueSeq()
			.toArray();
	}

	getKnownCount() {
		const {demographicsIMap} = this.props;

		this._knownCount = demographicsIMap.filter(
			values => !isBlank(values.getIn([0, 'value']))
		).size;
	}

	@autobind
	handleToggleBlankRows(event) {
		this.setState({
			hideBlanks: event.target.checked
		});
	}

	@autobind
	renderNav() {
		const {hideBlanks} = this.state;

		return (
			<Nav>
				<Nav.Item>
					<Checkbox
						checked={hideBlanks}
						label={Liferay.Language.get('hide-blank-attributes')}
						onChange={this.handleToggleBlankRows}
					/>
				</Nav.Item>
			</Nav>
		);
	}

	render() {
		const {
			props: {className, title},
			state: {hideBlanks}
		} = this;

		return (
			<Card
				className={getCN('entity-details-list-root', className)}
				pageDisplay
			>
				<Card.Header>
					<Card.Title>{title}</Card.Title>

					<div className='secondary-info'>
						{sub(
							Liferay.Language.get(
								'x-known-attributes-of-x-total'
							),
							[
								<b key='KNOWN'>{this._knownCount}</b>,
								<b key='TOTAL'>{this._detailsData.length}</b>
							],
							false
						)}
					</div>
				</Card.Header>

				<Card.Body noPadding>
					<SearchableEntityTableStateful
						columns={this.getColumns()}
						dataSourceFn={this.filterDetails}
						dataSourceParams={{hideBlanks}}
						internalSort
						navRenderer={this.renderNav}
						nowrap={false}
						rowIdentifier='name'
						showPagination={false}
					/>
				</Card.Body>
			</Card>
		);
	}
}
