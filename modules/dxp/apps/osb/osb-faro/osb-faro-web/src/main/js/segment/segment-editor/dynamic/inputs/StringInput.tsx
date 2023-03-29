import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import AutocompleteInput from 'shared/components/AutocompleteInput';
import Form from 'shared/components/form';
import getCN from 'classnames';
import React from 'react';
import {ClaySelectWithOption} from '@clayui/select';
import {ISegmentEditorInputBase} from '../utils/types';
import {isNull} from 'lodash';
import {isValid} from '../utils/utils';

interface IStringInputProps extends ISegmentEditorInputBase {
	touched: boolean;
	valid: boolean;
	value: string;
}

export default class StringInput extends React.Component<IStringInputProps> {
	_completedAnalytics = false;

	componentDidUpdate() {
		const {
			id,
			property: {entityName, type},
			valid
		} = this.props;

		if (!id && valid && !this._completedAnalytics) {
			this._completedAnalytics = true;

			analytics.track('Dynamic Segment Creation - Completed Attribute', {
				entityName,
				type
			});
		}
	}

	@autobind
	fieldValuesDataSourceFn() {
		const {
			channelId,
			groupId,
			property: {id},
			value
		} = this.props;

		return API.individuals
			.fetchFieldValues({
				channelId,
				fieldMappingFieldName: id,
				groupId,
				query: value
			})
			.then(({items}) => items);
	}

	@autobind
	handleBlur() {
		const {onChange, value} = this.props;

		onChange({touched: true, valid: isValid(value)});
	}

	@autobind
	handleChange(value) {
		this.props.onChange({valid: isValid(value), value});
	}

	render() {
		const {
			className,
			displayValue,
			operatorRenderer: OperatorDropdown,
			property: {entityName, options = []},
			touched,
			valid,
			value
		} = this.props;

		const showError = !valid && touched;

		return (
			<div className='criteria-statement'>
				<Form.Group autoFit>
					<Form.GroupItem className='entity-name' label shrink>
						{entityName}
					</Form.GroupItem>

					<Form.GroupItem className='display-value' label shrink>
						{displayValue}
					</Form.GroupItem>

					<OperatorDropdown />

					{!isNull(value) && (
						<Form.GroupItem>
							{options.length === 0 ? (
								<AutocompleteInput
									className={getCN(className, {
										'has-error': showError
									})}
									data-testid='value-input'
									dataSourceFn={this.fieldValuesDataSourceFn}
									onBlur={this.handleBlur}
									onChange={this.handleChange}
									value={value}
								/>
							) : (
								<ClaySelectWithOption
									className={getCN({
										'has-error': showError
									})}
									data-testid='value-select'
									onBlur={this.handleBlur}
									onChange={event =>
										this.handleChange(event.target.value)
									}
									options={options.map(o => ({
										label: o.label,
										value: o.value
									}))}
									value={value}
								/>
							)}
						</Form.GroupItem>
					)}
				</Form.Group>
			</div>
		);
	}
}
