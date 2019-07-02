import Icon from '../../shared/components/Icon';
import PortalComponent from '../../shared/components/header-controller/PortalComponent';
import React from 'react';

const MenuItem = ({children}) => {
	if (!children) return null;

	return <li>{children}</li>;
};

export default class DropDownHeader extends React.Component {
	render() {
		const {children} = this.props;
		const container = document.querySelector(
			'.user-control-group div.control-menu-icon'
		);

		return (
			<PortalComponent container={container} replace>
				<div className='dropdown lfr-icon-menu nav-item portlet-options'>
					<a
						aria-expanded='false'
						aria-haspopup='true'
						className='direction-right dropdown-toggle icon-monospaced'
						data-toggle='dropdown'
						href='javascript:;'
						role='button'
						title='Options'
					>
						<span>
							<Icon iconName='ellipsis-v' />
						</span>
					</a>

					<div
						className='dropdown-menu dropdown-menu-header dropdown-menu-right'
						role='menu'
					>
						<ul className='list-unstyled'>{children}</ul>
					</div>
				</div>
			</PortalComponent>
		);
	}
}

DropDownHeader.Item = MenuItem;
