import autobind from 'autobind-decorator';
import Icon from '../Icon';
import PageSizeItem from './PageSizeItem';
import React from 'react';

/**
 * @class
 * @memberof shared/components
 */
export default class PageSizeEntries extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			selectedPageSize: props.selectedPageSize
		};
	}

	@autobind
	setPageSize(pageSize) {
		const { onSelectPageSize } = this.props;

		this.setState({ selectedPageSize: pageSize });
		onSelectPageSize(pageSize);
	}

	render() {
		const { pageSizeEntries } = this.props;
		const { selectedPageSize } = this.state;

		return (
			<div className="dropdown pagination-items-per-page">
				<a
					aria-expanded="false"
					aria-haspopup="true"
					className="dropdown-toggle"
					data-toggle="dropdown"
					href="#1"
					role="button"
				>
					{`${selectedPageSize} ${Liferay.Language.get('entries')}`}
					<Icon iconName="caret-double-l" />
				</a>
				<div className="dropdown-menu dropdown-menu-top">
					{pageSizeEntries.map((pageSize, index) => (
						<PageSizeItem
							key={`${index}_${pageSize}`}
							onChangePageSize={this.setPageSize}
							pageSize={pageSize}
						/>
					))}
				</div>
			</div>
		);
	}
}