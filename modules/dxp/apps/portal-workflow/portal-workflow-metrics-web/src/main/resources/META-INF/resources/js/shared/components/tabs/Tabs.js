import Icon from '../Icon';
import React from 'react';
import TabItem from './TabItem';
import {withRouter} from 'react-router-dom';

class Tabs extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			expanded: false
		};
	}

	hideNavbar() {
		this.setState({
			expanded: false
		});
	}

	toggleExpanded() {
		this.setState({
			expanded: !this.state.expanded
		});
	}

	render() {
		const {expanded} = this.state;
		const {
			location: {pathname, search},
			tabs
		} = this.props;

		const isActive = tab => pathname.includes(tab.key);

		const activeTab = tabs.filter(isActive)[0] || {};

		let navbarClassName = '';
		let togglerClassName = 'collapsed';

		if (expanded) {
			navbarClassName = 'show';
			togglerClassName = '';
		}

		return (
			<nav className='navbar navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-secondary'>
				<div className='container-fluid container-fluid-max-xl'>
					<button
						aria-expanded={expanded}
						aria-label={Liferay.Language.get('toggle-navigation')}
						className={`${togglerClassName} navbar-toggler navbar-toggler-link`}
						onClick={this.toggleExpanded.bind(this)}
					>
						{activeTab.name}

						<Icon iconName='caret-bottom' />
					</button>

					<div
						className={`collapse navbar-collapse ${navbarClassName}`}
					>
						<div className='container-fluid container-fluid-max-xl'>
							<ul className='navbar-nav'>
								{tabs.map((tab, index) => (
									<li
										className='nav-item'
										key={index}
										onClick={this.hideNavbar.bind(this)}
									>
										<TabItem
											{...tab}
											active={isActive(tab)}
											query={search}
										/>
									</li>
								))}
							</ul>
						</div>
					</div>
				</div>
			</nav>
		);
	}
}

export default withRouter(Tabs);
export {Tabs};
