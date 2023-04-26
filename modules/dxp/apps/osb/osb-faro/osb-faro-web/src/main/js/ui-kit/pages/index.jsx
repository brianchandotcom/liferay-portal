import autobind from 'autobind-decorator';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import Nav from 'shared/components/Nav';
import React from 'react';
import SearchInput from 'shared/components/SearchInput';
import {find} from 'lodash';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';

const req = require.context('.', false, /\w+Kit.(jsx|tsx)$/);

const kits = req.keys().map((kit, id) => ({
	component: req(kit, id).default,
	id,
	name: kit.replace('./', '').replace(/Kit\.(jsx|tsx)/, '')
}));

export default class UIKit extends React.Component {
	static defaultProps = {
		name: kits[0].name
	};

	static propTypes = {
		groupId: PropTypes.string.isRequired,
		history: PropTypes.object.isRequired,
		name: PropTypes.string
	};

	state = {
		query: ''
	};

	@autobind
	handleChange(value) {
		this.setState({
			query: value
		});
	}

	filterKits() {
		const {query} = this.state;

		return query
			? kits.filter(kit =>
					kit.name.toLowerCase().includes(query.toLowerCase())
			  )
			: kits;
	}

	render() {
		const {channelId, groupId, history, name: componentName} = this.props;

		const {query} = this.state;

		const selectedItem = find(kits, ['name', componentName]) || kits[0];

		if (!componentName) {
			history.push(
				toRoute(Routes.UI_KIT, {name: selectedItem.name.toLowerCase()})
			);
		}

		const kitList = this.filterKits(query);

		const ComponentKit = selectedItem.component;

		return (
			<BasePage pageTitle='UI Kit'>
				<BasePage.Body>
					<div className='container-fluid-xxl ui-kit-root'>
						<Card>
							<Card.Body>
								<div className='row'>
									<div className='col-sm-3 sidebar'>
										<form className='navbar-form navbar-form-autofit'>
											<SearchInput
												onChange={this.handleChange}
												placeholder={Liferay.Language.get(
													'filter'
												)}
												value={query}
											/>
										</form>

										<h4>{'Component List'}</h4>

										<div className='active-item-header'>
											{'Now Showing: '}
											<span>{componentName}</span>
										</div>

										<Nav display='stacked'>
											{kitList.map(({name}) => (
												<Nav.Item
													active={
														name == componentName
													}
													href={toRoute(
														Routes.UI_KIT,
														{
															channelId,
															groupId,
															name
														}
													)}
													key={name}
												>
													{name}
												</Nav.Item>
											))}
										</Nav>
									</div>

									<div className='col-sm-9'>
										<h2>
											{componentName} {'Component'}
										</h2>

										<div className='component-preview-container'>
											<Card>
												<Card.Body>
													<ComponentKit
														groupId={groupId}
													/>
												</Card.Body>
											</Card>
										</div>
									</div>
								</div>
							</Card.Body>
						</Card>
					</div>
				</BasePage.Body>
			</BasePage>
		);
	}
}
