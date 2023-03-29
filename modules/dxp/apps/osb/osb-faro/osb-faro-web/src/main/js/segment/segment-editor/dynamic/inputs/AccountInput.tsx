import * as API from 'shared/api';
import autobind from 'autobind-decorator';
import CustomNumberInput from './CustomNumberInput';
import CustomStringInput from './CustomStringInput';
import Promise from 'metal-promise';
import React from 'react';
import {getPropertyValue} from '../utils/custom-inputs';
import {ISegmentEditorCustomInputBase} from '../utils/types';
import {PropertyTypes} from '../utils/constants';

interface IAccountInputProps extends ISegmentEditorCustomInputBase {
	touched: boolean;
	valid: boolean;
}

export default class AccountInput extends React.Component<IAccountInputProps> {
	@autobind
	fieldValuesDataSourceFn(): Promise<string[]> {
		const {
			groupId,
			property: {id},
			value: valueIMap
		} = this.props;

		return API.accounts
			.fetchFieldValues({
				fieldMappingFieldName: id,
				groupId,
				query: getPropertyValue(valueIMap, 'value', 0)
			})
			.then(({items}) => items);
	}

	render() {
		const {
			property: {type}
		} = this.props;

		if (type === PropertyTypes.AccountNumber) {
			return <CustomNumberInput {...this.props} />;
		}

		return (
			<CustomStringInput
				{...this.props}
				fieldValuesDataSourceFn={this.fieldValuesDataSourceFn}
			/>
		);
	}
}
