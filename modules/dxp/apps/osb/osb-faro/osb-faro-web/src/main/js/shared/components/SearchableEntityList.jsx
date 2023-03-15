import autobind from 'autobind-decorator';
import BaseResults from 'shared/components/BaseResults';
import EntityList from 'shared/components/EntityList';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {PropTypes} from 'prop-types';

class SearchableEntityList extends React.Component {
	static defaultProps = {
		showCheckbox: false
	};

	static propTypes = {
		dataSourceParams: PropTypes.object,
		entityLabel: PropTypes.string,
		groupId: PropTypes.string.isRequired,
		showCheckbox: PropTypes.bool
	};

	@autobind
	renderEntityList({
		checkedItemsISet,
		className,
		items,
		loading,
		onSelectItemsChange,
		total
	}) {
		const {entityLabel, groupId} = this.props;

		return (
			<EntityList
				className={className}
				groupId={groupId}
				header={entityLabel}
				items={items}
				loading={loading}
				noItemsHeader={Liferay.Language.get('search-results')}
				onSelectItemsChange={onSelectItemsChange}
				selectedItemsISet={checkedItemsISet}
				total={total}
			/>
		);
	}

	render() {
		const {
			dataSourceParams,
			entityLabel,
			groupId,
			showCheckbox,
			...otherProps
		} = this.props;

		return (
			<BaseResults
				{...omitDefinedProps(
					otherProps,
					SearchableEntityList.propTypes
				)}
				dataSourceParams={{...dataSourceParams, groupId}}
				entityLabel={entityLabel}
				resultsRenderer={this.renderEntityList}
				showCheckbox={showCheckbox}
			/>
		);
	}
}

export default SearchableEntityList;
