import {
	pushToHistory,
	removeItem
} from '../../../shared/components/filter/util/filterUtil';
import autobind from 'autobind-decorator';
import Icon from '../../../shared/components/Icon';
import React from 'react';
import {withRouter} from 'react-router-dom';

class FilterResultsItem extends React.Component {
	@autobind
	onRemoveButtonClick() {
		const {
			filter,
			item,
			location: {search}
		} = this.props;

		const filterQuery = removeItem(filter.key, item, search);

		pushToHistory(filterQuery, this.props);
	}

	render() {
		const {filter, item} = this.props;

		return (
			<li className='tbar-item'>
				<div className='tbar-section'>
					<span className='component-label label label-dismissible tbar-label'>
						<span className='label-item label-item-expand'>
							<div className='label-section'>
								<span className='font-weight-normal'>{`${
									filter.name
								}: `}</span>

								<strong>{item.name}</strong>
							</div>
						</span>
						<span className='label-item label-item-after'>
							<button
								aria-label='close'
								className='btn close'
								onClick={this.onRemoveButtonClick}
								type='button'
							>
								<Icon iconName='times' />
							</button>
						</span>
					</span>
				</div>
			</li>
		);
	}
}

export default withRouter(FilterResultsItem);
export {FilterResultsItem};