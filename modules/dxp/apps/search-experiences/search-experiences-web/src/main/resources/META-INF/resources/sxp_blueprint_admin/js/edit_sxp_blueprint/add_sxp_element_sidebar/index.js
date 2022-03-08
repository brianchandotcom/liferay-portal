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
import {useIsMounted} from '@liferay/frontend-js-react-web';
import PropTypes from 'prop-types';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import LearnMessage from '../../shared/LearnMessage';
import SearchInput from '../../shared/SearchInput';
import Sidebar from '../../shared/Sidebar';
import ThemeContext from '../../shared/ThemeContext';
import {
	CUSTOM_JSON_SXP_ELEMENT,
	DEFAULT_SXP_ELEMENT_ICON,
} from '../../utils/data';
import {addParams, fetchData} from '../../utils/fetch';
import {getLocalizedText} from '../../utils/language';
import {setItemAddSXPElementSidebar} from '../../utils/sessionStorage';

const DEFAULT_CATEGORY = 'other';
const DEFAULT_EXPANDED_LIST = ['match'];

const LAST_CATEGORIES = [DEFAULT_CATEGORY, 'custom'];

const SXPElementList = ({category, expand, onAddSXPElement, sxpElements}) => {
	const {locale} = useContext(ThemeContext);

	const [showList, setShowList] = useState(expand);

	useEffect(() => {
		setShowList(expand);
	}, [expand]);

	const _handleAddSXPElement = (sxpElement) => () => {
		onAddSXPElement(sxpElement);
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
					{sxpElements.map((sxpElement, index) => {
						const description = getLocalizedText(
							sxpElement.description_i18n,
							locale
						);
						const title = getLocalizedText(
							sxpElement.title_i18n,
							locale
						);

						return (
							<ClayList.Item
								className="sxp-element-item"
								flex
								key={index}
							>
								<ClayList.ItemField>
									<ClaySticker size="md">
										<ClayIcon
											symbol={
												sxpElement.elementDefinition
													?.icon ||
												DEFAULT_SXP_ELEMENT_ICON
											}
										/>
									</ClaySticker>
								</ClayList.ItemField>

								<ClayList.ItemField expand>
									{title && (
										<ClayList.ItemTitle>
											{title}
										</ClayList.ItemTitle>
									)}

									{description && (
										<ClayList.ItemText subtext={true}>
											{description}
										</ClayList.ItemText>
									)}
								</ClayList.ItemField>

								<ClayList.ItemField>
									<div className="add-sxp-element-button-background" />

									<ClayButton
										aria-label={Liferay.Language.get('add')}
										className="add-sxp-element-button"
										displayType="secondary"
										onClick={_handleAddSXPElement(
											sxpElement
										)}
										small
									>
										{Liferay.Language.get('add')}
									</ClayButton>
								</ClayList.ItemField>
							</ClayList.Item>
						);
					})}
				</ClayList>
			)}
		</>
	);
};

function AddSXPElement({
	emptyMessage = Liferay.Language.get('no-query-elements-found'),
	onAddSXPElement,
	querySXPElements,
}) {
	const {locale} = useContext(ThemeContext);

	const [loading, setLoading] = useState(true);

	const sxpElements = useMemo(
		() => [...querySXPElements, CUSTOM_JSON_SXP_ELEMENT],
		[querySXPElements]
	);

	const [filteredSXPElements, setFilteredSXPElements] = useState(sxpElements);

	const [categories, setCategories] = useState([]);
	const [categorizedSXPElements, setCategorizedSXPElements] = useState({});
	const [expandAll, setExpandAll] = useState(false);

	const _categorizeSXPElements = (sxpElements) => {
		const newCategories = [];
		const newCategorizedSXPElements = {};

		sxpElements.map((sxpElement) => {
			const category =
				sxpElement.elementDefinition?.category || DEFAULT_CATEGORY;

			newCategorizedSXPElements[category] = [
				...(newCategorizedSXPElements[category] || []),
				sxpElement,
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
					newCategorizedSXPElements[category] &&
					newCategorizedSXPElements[category].length
			), // Add last categories unless there are no elements
		]);

		setCategorizedSXPElements(newCategorizedSXPElements);
	};

	useEffect(() => {
		_categorizeSXPElements(sxpElements);

		setLoading(false);
	}, [sxpElements]);

	const _handleSearchChange = useCallback(
		(value) => {
			const newSXPElements = sxpElements.filter((sxpElement) => {
				if (value) {
					const sxpElementTitle = getLocalizedText(
						sxpElement.title_i18n,
						locale
					);

					return sxpElementTitle
						.toLowerCase()
						.includes(value.toLowerCase());
				}
				else {
					return true;
				}
			});

			_categorizeSXPElements(newSXPElements);
			setFilteredSXPElements(newSXPElements);
			setExpandAll(!!value);
		},
		[sxpElements, locale]
	);

	return (
		<>
			<LearnMessage resourceKey="query-elements" />

			<nav className="component-tbar sidebar-search tbar">
				<div className="container-fluid">
					<SearchInput onChange={_handleSearchChange} />
				</div>
			</nav>

			{!loading ? (
				filteredSXPElements.length ? (
					<div className="sxp-element-list">
						{categories.map((category) => (
							<SXPElementList
								category={category}
								expand={
									expandAll ||
									DEFAULT_EXPANDED_LIST.includes(category)
								}
								key={category}
								onAddSXPElement={onAddSXPElement}
								sxpElements={categorizedSXPElements[category]}
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
		</>
	);
}

function AddSXPElementSidebar({
	emptyMessage,
	onAddSXPElement,
	onClose,
	visible,
}) {
	const {defaultLocale} = useContext(ThemeContext);
	const isMounted = useIsMounted();

	const [querySXPElements, setQuerySXPElements] = useState(null);

	// TODO check pagesize

	useEffect(() => {
		fetchData(
			addParams('/o/search-experiences-rest/v1.0/sxp-elements', {
				pageSize: 200,
			}),
			{method: 'GET'},
			(responseContent) => {
				if (isMounted()) {
					setQuerySXPElements(
						responseContent.items.map(
							({
								description,
								description_i18n,
								title,
								title_i18n,
								...props
							}) => ({
								...props,
								description_i18n: description_i18n || {
									[defaultLocale]: description,
								},
								title_i18n: title_i18n || {
									[defaultLocale]: title,
								},
							})
						)
					);
				}
			},
			() => {
				if (isMounted()) {
					setQuerySXPElements([]);
				}
			}
		);
	}, []); //eslint-disable-line

	if (!querySXPElements) {
		return null;
	}

	const _handleClose = () => {
		setItemAddSXPElementSidebar('closed');

		onClose();
	};

	return (
		<Sidebar
			className="add-sxp-element-sidebar"
			onClose={_handleClose}
			title={Liferay.Language.get('add-query-elements')}
			visible={visible}
		>
			<AddSXPElement
				emptyMessage={emptyMessage}
				onAddSXPElement={onAddSXPElement}
				querySXPElements={querySXPElements}
			/>
		</Sidebar>
	);
}

AddSXPElementSidebar.propTypes = {
	emptyMessage: PropTypes.string,
	onAddSXPElement: PropTypes.func,
	onClose: PropTypes.func,
	visible: PropTypes.bool,
};

export default React.memo(AddSXPElementSidebar);
