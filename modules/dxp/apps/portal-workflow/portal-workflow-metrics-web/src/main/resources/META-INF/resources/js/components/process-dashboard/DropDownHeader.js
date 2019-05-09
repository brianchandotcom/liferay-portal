import Icon from '../../shared/components/Icon';
import React from 'react';
import ReactDOM from 'react-dom';

const MenuItem = ({ children }) => {
	if (!children) return null;

	return <li>{children}</li>;
};

export default class DropDownHeader extends React.Component {
	componentWillUnmount() {
		ReactDOM.unmountComponentAtNode(this.container);
	}

	get container() {
		return document.querySelector('.user-control-group div.control-menu-icon');
	}

	render() {
		const { children } = this.props;

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
					this.container
				)}
			</React.Fragment>
		);
	}
}

DropDownHeader.Item = MenuItem;