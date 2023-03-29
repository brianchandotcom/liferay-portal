import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import SearchableSelect from 'shared/components/SearchableSelect';
import {Map} from 'immutable';
import {PropTypes} from 'prop-types';

export default class FieldDropDown extends React.Component {
	static propTypes = {
		dataIMap: PropTypes.instanceOf(Map).isRequired,
		name: PropTypes.string,
		onFieldPreviewModal: PropTypes.func,
		onSearchInput: PropTypes.func,
		onSearchSelect: PropTypes.func,
		placeholder: PropTypes.string,
		readOnly: PropTypes.bool,
		searchInputValue: PropTypes.string,
		searchItems: PropTypes.array,
		title: PropTypes.oneOfType([PropTypes.array, PropTypes.string])
	};

	render() {
		const {
			className,
			dataIMap,
			name,
			onSearchInput,
			onSearchSelect,
			placeholder,
			readOnly,
			searchInputValue,
			searchItems,
			title,
			...otherProps
		} = this.props;

		const data = dataIMap.toJS();

		return (
			<div className={className}>
				{title && <label className='text-truncate'>{title}</label>}

				<SearchableSelect
					{...omitDefinedProps(otherProps, FieldDropDown.propTypes)}
					buttonPlaceholder={(data && data.name) || placeholder}
					caretDouble
					inputPlaceholder={Liferay.Language.get('search')}
					inputValue={searchInputValue}
					items={searchItems}
					name={name}
					onSearchChange={onSearchInput}
					onSelect={onSearchSelect}
					readOnly={readOnly}
					selectedItem={data}
				/>

				<div className='example-value text-truncate'>
					{data && data.value}
				</div>
			</div>
		);
	}
}
