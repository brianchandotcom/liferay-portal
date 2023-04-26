import autobind from 'autobind-decorator';
import Dropdown from './Dropdown';
import faroConstants from 'shared/util/constants';
import getCN from 'classnames';
import NavBar from './NavBar';
import Pagination from './Pagination';
import React from 'react';
import {getPluralMessage} from '../util/lang';
import {PropTypes} from 'prop-types';
import {setUriQueryValues} from 'shared/util/router';

const {cur: DEFAULT_CUR, deltaValues} = faroConstants.pagination;

const FIRST_PAGE = 1;

const MAXIMUM_DELTA = 75;

const SIZES = ['sm', 'lg'];

class DeltaItem extends React.Component {
	static propTypes = {
		delta: PropTypes.number.isRequired,
		onChange: PropTypes.func
	};

	@autobind
	handleChange() {
		const {delta, onChange} = this.props;

		if (onChange) {
			onChange(delta);
		}
	}

	render() {
		return (
			<Dropdown.Item {...this.props} onClick={this.handleChange}>
				{this.props.children}
			</Dropdown.Item>
		);
	}
}

class PaginationBar extends React.Component {
	static defaultProps = {
		deltas: deltaValues,
		selectedDelta: deltaValues[1],
		showDeltaDropdown: true,
		showResultsMessage: true,
		totalItems: 0
	};

	static propTypes = {
		deltas: PropTypes.array,
		href: PropTypes.string,
		onDeltaChange: PropTypes.func,
		onPageChange: PropTypes.func,
		page: PropTypes.number,
		selectedDelta: PropTypes.number,
		showDeltaDropdown: PropTypes.bool,
		showResultsMessage: PropTypes.bool,
		size: PropTypes.oneOf(SIZES),
		totalItems: PropTypes.number
	};

	state = {};

	getStartedItem(start, selectedDelta, totalItems) {
		let currentStart = this.startItem(start, selectedDelta);

		if (currentStart > totalItems) {
			currentStart = this.startItem(
				Math.ceil(totalItems / selectedDelta) * selectedDelta,
				selectedDelta
			);
		}

		return currentStart.toLocaleString();
	}

	startItem(start, selectedDelta) {
		return start - selectedDelta + 1;
	}

	render() {
		const {
			className,
			deltas,
			href,
			onDeltaChange,
			onPageChange,
			showDeltaDropdown,
			showResultsMessage,
			size,
			totalItems
		} = this.props;

		const classes = getCN('pagination-bar-root', className, {
			[`pagination-${size}`]: size
		});

		const page = Math.max(this.props.page, FIRST_PAGE) || FIRST_PAGE;

		const selectedDelta =
			Math.min(this.props.selectedDelta, MAXIMUM_DELTA) || MAXIMUM_DELTA;

		const start = page * selectedDelta;

		return (
			<NavBar className={classes}>
				{showDeltaDropdown && (
					<Dropdown
						buttonProps={{
							displayType: 'unstyled',
							size: 'sm'
						}}
						className='pagination-items-per-page'
						label={getPluralMessage(
							Liferay.Language.get('x-item'),
							Liferay.Language.get('x-items'),
							selectedDelta
						)}
					>
						{deltas.map(item => (
							<DeltaItem
								delta={item}
								hideOnClick
								href={
									onDeltaChange
										? null
										: setUriQueryValues({
												delta: item,
												page: DEFAULT_CUR
										  })
								}
								key={item}
								onChange={onDeltaChange}
							>
								{item}
							</DeltaItem>
						))}
					</Dropdown>
				)}

				{showResultsMessage && (
					<div
						className='pagination-results'
						key='PAGINATION_RESULTS'
					>
						{getPluralMessage(
							Liferay.Language.get('showing-x-to-x-of-x-entry'),
							Liferay.Language.get('showing-x-to-x-of-x-entries'),
							totalItems,
							true,
							[
								this.getStartedItem(
									start,
									selectedDelta,
									totalItems
								),
								Math.min(start, totalItems).toLocaleString(),
								totalItems.toLocaleString()
							]
						)}
					</div>
				)}

				<Pagination
					href={href}
					onChange={onPageChange}
					page={
						page <= totalItems
							? page
							: Math.ceil(totalItems / selectedDelta)
					}
					total={Math.ceil(totalItems / selectedDelta)}
				/>
			</NavBar>
		);
	}
}

export default PaginationBar;
