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

const DEFAULT_CATEGORY = 'other';
const DEFAULT_EXPANDED_LIST = ['match'];

const LAST_CATEGORIES = [DEFAULT_CATEGORY, 'custom'];

const ElementList = ({category, elements, expand, onAddElement}) => {
	const {locale} = useContext(ThemeContext);

	const [showList, setShowList] = useState(expand);

	useEffect(() => {
		setShowList(expand);
	}, [expand]);

	const _handleAddElement = (
		elementTemplateJSON,
		uiConfigurationJSON
	) => () => {
		onAddElement({
			elementTemplateJSON,
			uiConfigurationJSON,
		});
	};

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
					{elements.map(
						({elementTemplateJSON, uiConfigurationJSON}, index) => {
							return (
								<ClayList.Item
									className="element-item"
									flex
									key={index}
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
										<div className="add-element-button-background" />

										<ClayButton
											aria-label={Liferay.Language.get(
												'add'
											)}
											className="add-element-button"
											displayType="secondary"
											onClick={_handleAddElement(
												elementTemplateJSON,
												uiConfigurationJSON
											)}
											small
										>
											{Liferay.Language.get('add')}
										</ClayButton>
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

function AddElementSidebar({
	elements = [],
	emptyMessage,
	onAddElement,
	onClose,
	title,
	visible,
}) {
	const {locale} = useContext(ThemeContext);

	const [loading, setLoading] = useState(true);

	const [filteredElements, setFilteredElements] = useState(elements);

	const [categories, setCategories] = useState([]);
	const [categorizedElements, setCategorizedElements] = useState({});
	const [expandAll, setExpandAll] = useState(false);

	const _categorizeElements = (elements) => {
		const newCategories = [];
		const newCategorizedElements = {};

		elements.map((element) => {
			const category =
				element.elementTemplateJSON.category || DEFAULT_CATEGORY;

			newCategorizedElements[category] = [
				...(newCategorizedElements[category] || []),
				element,
			];

			// Don't add last categories since they will be added in the
			// `setCategories` call below

			if (
				!newCategories.includes(category) &&
				!LAST_CATEGORIES.includes(category)
			) {
				newCategories.push(category);
			}
		});

		setCategories([
			...newCategories.sort(),
			...LAST_CATEGORIES.filter(
				(category) =>
					newCategorizedElements[category] &&
					newCategorizedElements[category].length
			), // Add last categories unless there are no elements
		]);

		setCategorizedElements(newCategorizedElements);
	};

	useEffect(() => {
		_categorizeElements(elements);

		setLoading(false);
	}, [elements]);

	const _handleSearchChange = useCallback(
		(value) => {
			const newElements = elements.filter((element) => {
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

			_categorizeElements(newElements);
			setFilteredElements(newElements);
			setExpandAll(!!value);
		},
		[elements, locale]
	);

	return (
		<div
			className={getCN(
				'add-element-sidebar',
				'sidebar',
				'sidebar-light',
				{open: visible}
			)}
		>
			<div className="sidebar-header">
				<h4 className="component-title">
					<span className="text-truncate-inline">
						<span className="text-truncate">{title}</span>
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
				filteredElements.length ? (
					<div className="element-list">
						{categories.map((category) => (
							<ElementList
								category={category}
								elements={categorizedElements[category]}
								expand={
									expandAll ||
									DEFAULT_EXPANDED_LIST.includes(category)
								}
								key={category}
								onAddElement={onAddElement}
							/>
						))}
					</div>
				) : (
					<div className="empty-list-message">
						<ClayEmptyState description="" title={emptyMessage} />
					</div>
				)
			) : (
				<ClayLoadingIndicator />
			)}
		</div>
	);
}

AddElementSidebar.propTypes = {
	elements: PropTypes.arrayOf(PropTypes.object),
	emptyMessage: PropTypes.string,
	onAddElement: PropTypes.func,
	onClose: PropTypes.func,
	title: PropTypes.string,
	visible: PropTypes.bool,
};

export default React.memo(AddElementSidebar);
