import Icon from '../../shared/components/Icon';
import React from 'react';
import ReactDOM from 'react-dom';

const MenuItem = ({ children }) => {
	if (!children) return null;

	return <li>{children}</li>;
};

export default class DropDownHeader extends React.Component {
	render() {
		const { children } = this.props;

		const container = document.querySelector(
			'.user-control-group div.control-menu-icon'
		);

		return (
			<React.Fragment>
				{ReactDOM.createPortal(
					<div className="dropdown lfr-icon-menu nav-item portlet-options">
						<a
							aria-expanded="false"
							aria-haspopup="true"
							className="direction-right dropdown-toggle icon-monospaced"
							data-toggle="dropdown"
							href="javascript:;"
							role="button"
							title="Options"
						>
							<span>
								<Icon iconName="ellipsis-v" />
							</span>
						</a>

						<div
							className="dropdown-menu dropdown-menu-header dropdown-menu-right"
							role="menu"
						>
							<ul className="list-unstyled">{children}</ul>
						</div>
					</div>,
					container
				)}
			</React.Fragment>
		);
	}
}

DropDownHeader.Item = MenuItem;