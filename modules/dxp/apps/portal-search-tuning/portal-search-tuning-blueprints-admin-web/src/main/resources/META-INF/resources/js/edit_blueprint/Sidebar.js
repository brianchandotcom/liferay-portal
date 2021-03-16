/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React, {useCallback, useContext, useEffect, useState} from 'react';

import SearchInput from '../shared/SearchInput';
import ThemeContext from '../shared/ThemeContext';

const EmptyListMessage = () => (
	<div className="empty-list-message">
		<ClayEmptyState
			title={Liferay.Language.get('no-query-elements-found')}
		/>
	</div>
);

const DEFAULT_EXPANDED_LIST = ['match'];

const QueryElementList = ({category, expand, onAddElement, queryElements}) => {
	const {locale} = useContext(ThemeContext);

	const [showAdd, setShowAdd] = useState(-1);
	const [showList, setShowList] = useState(expand);

	useEffect(() => {
		setShowList(expand);
	}, [expand]);

	return (
		<>
			{!!category && (
				<ClayButton
					className="panel-header sidebar-dt"
					displayType="unstyled"
					onClick={() => setShowList(!showList)}
				>
					<span>{category}</span>

					<span className="sidebar-arrow">
						<ClayIcon
							symbol={showList ? 'angle-down' : 'angle-right'}
						/>
					</span>
				</ClayButton>
			)}

			{showList && (
				<ClayList>
					{queryElements.map(
						({elementTemplateJSON, uiConfigurationJSON}, index) => {
							return (
								<ClayList.Item
									className={`${
										showAdd === index ? 'hover' : ''
									}`}
									flex
									key={index}
									onMouseEnter={() => setShowAdd(index)}
									onMouseLeave={() => setShowAdd(-1)}
								>
									<ClayList.ItemField>
										<ClaySticker size="md">
											<ClayIcon
												symbol={
													elementTemplateJSON.icon
												}
											/>
										</ClaySticker>
									</ClayList.ItemField>

									<ClayList.ItemField expand>
										<ClayList.ItemTitle>
											{elementTemplateJSON.title[
												locale
											] || elementTemplateJSON.title}
										</ClayList.ItemTitle>

										{elementTemplateJSON.description &&
											Object.keys(
												elementTemplateJSON.description
											).length > 0 && (
												<ClayList.ItemText
													subtext={true}
												>
													{elementTemplateJSON
														.description[locale] ||
														elementTemplateJSON.description}
												</ClayList.ItemText>
											)}
									</ClayList.ItemField>

									<ClayList.ItemField>
										{showAdd === index && (
											<div className="button-wrapper">
												<div className="add-element-button">
													<ClayButton
														aria-label={Liferay.Language.get(
															'add'
														)}
														displayType="secondary"
														onClick={() => {
															onAddElement({
																elementTemplateJSON,
																uiConfigurationJSON,
															});
														}}
														small
													>
														{Liferay.Language.get(
															'add'
														)}
													</ClayButton>
												</div>
											</div>
										)}
									</ClayList.ItemField>
								</ClayList.Item>
							);
						}
					)}
				</ClayList>
			)}
		</>
	);
};

function Sidebar({elements = [], onAddElement, onClose, visible}) {
	const {locale} = useContext(ThemeContext);

	const [loading, setLoading] = useState(true);

	const [queryElements, setQueryElements] = useState(elements);

	const [categories, setCategories] = useState([]);
	const [categorizedElements, setCategorizedElements] = useState({});
	const [expandAll, setExpandAll] = useState(false);

	const categorizeElements = (elements) => {
		const newCategories = [];
		const newCategorizedElements = {};

		elements.map((element) => {
			const category = element.elementTemplateJSON.category
				? element.elementTemplateJSON.category
				: Liferay.Language.get('other');

			if (newCategorizedElements[category]) {
				newCategorizedElements[category] = [
					...newCategorizedElements[category],
					element,
				];
			}
			else {
				newCategories.push(category);
				newCategorizedElements[category] = [element];
			}
		});

		setCategories(newCategories);
		setCategorizedElements(newCategorizedElements);
	};

	useEffect(() => {
		categorizeElements(elements);

		setLoading(false);
	}, [elements]);

	const _handleSearchChange = useCallback(
		(value) => {
			const newQueryElements = elements.filter((element) => {
				if (value) {
					const elementTitle =
						element.elementTemplateJSON.title[locale] ||
						element.elementTemplateJSON.title;

					return elementTitle
						.toLowerCase()
						.includes(value.toLowerCase());
				}
				else {
					return true;
				}
			});

			categorizeElements(newQueryElements);
			setQueryElements(newQueryElements);
			setExpandAll(!!value);
		},
		[elements, locale]
	);

	return (
		<div className={getCN('sidebar', 'sidebar-light', {open: visible})}>
			<div className="sidebar-header">
				<h4 className="component-title">
					<span className="text-truncate-inline">
						<span className="text-truncate">
							{Liferay.Language.get('add-query-elements')}
						</span>
					</span>
				</h4>

				<ClayButton
					aria-label={Liferay.Language.get('close')}
					displayType="unstyled"
					onClick={onClose}
					small
				>
					<ClayIcon symbol="times" />
				</ClayButton>
			</div>

			<nav className="component-tbar sidebar-search tbar">
				<div className="container-fluid">
					<SearchInput onChange={_handleSearchChange} />
				</div>
			</nav>

			{!loading ? (
				queryElements.length ? (
					<div className="query-element-list">
						{categories.map((category) => (
							<QueryElementList
								category={category}
								expand={
									expandAll ||
									DEFAULT_EXPANDED_LIST.includes(category)
								}
								key={category}
								onAddElement={onAddElement}
								queryElements={categorizedElements[category]}
							/>
						))}
					</div>
				) : (
					<EmptyListMessage />
				)
			) : (
				<ClayLoadingIndicator />
			)}
		</div>
	);
}

Sidebar.propTypes = {
	elements: PropTypes.arrayOf(PropTypes.object),
	onAddElement: PropTypes.func,
	onClose: PropTypes.func,
	visible: PropTypes.bool,
};

export default React.memo(Sidebar);
