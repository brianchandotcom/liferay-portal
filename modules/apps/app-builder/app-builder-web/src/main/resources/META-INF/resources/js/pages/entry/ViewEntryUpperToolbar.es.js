/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayButtonGroup from '@clayui/button/lib/Group';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useContext} from 'react';
import {withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import UpperToolbar from '../../components/upper-toolbar/UpperToolbar.es';
import usePermissions from '../../hooks/usePermissions.es';
import {confirmDelete} from '../../utils/client.es';
import {sub} from '../../utils/lang.es';
import {navigateToEditPage} from './utils.es';

function ViewEntryUpperToolbar({
	additionalButtons,
	children,
	dataRecordId,
	history,
	page,
	showButtons,
	totalCount,
}) {
	const {
		appDeploymentType,
		basePortletURL,
		showFormView,
		userLanguageId,
	} = useContext(AppContext);
	const permissions = usePermissions();

	const changeEntryIndex = (entryIndex) => {
		const {hash} = window.location;
		const newHash = hash.substr(hash.indexOf('?') + 1);
		const baseURL = `/entries/${entryIndex}`;

		history.push(`${baseURL}?${newHash}`);
	};

	const onDelete = () => {
		confirmDelete('/o/data-engine/v2.0/data-records/', {
			successMessage: Liferay.Language.get('an-entry-was-deleted'),
		})({
			id: dataRecordId,
		}).then((confirmed) => {
			if (confirmed) {
				history.push('/');
			}
		});
	};

	const onEdit = () => {
		navigateToEditPage(basePortletURL, {
			dataRecordId,
			languageId: userLanguageId,
			redirect: location.href,
		});
	};

	const onNext = () => {
		const nextIndex = Math.min(parseInt(page, 10) + 1, totalCount);
		changeEntryIndex(nextIndex);
	};

	const onPrev = () => {
		const prevIndex = Math.max(parseInt(page, 10) - 1, 1);
		changeEntryIndex(prevIndex);
	};

	const showDeleteButton = showButtons?.delete ?? true;
	const showUpdateButton = showButtons?.update ?? true;

	return (
		<UpperToolbar className={appDeploymentType}>
			<UpperToolbar.Item className="ml-2 text-left">
				<label>
					{totalCount > 0 &&
						sub(
							totalCount == 1
								? Liferay.Language.get('x-of-x-entry')
								: Liferay.Language.get('x-of-x-entries'),
							[page, totalCount]
						)}
				</label>
			</UpperToolbar.Item>

			<UpperToolbar.Item expand>{children}</UpperToolbar.Item>

			<UpperToolbar.Group>
				<ClayTooltipProvider>
					<ClayButtonGroup>
						<ClayButtonWithIcon
							data-tooltip-align="bottom"
							data-tooltip-delay="200"
							disabled={page === 1}
							displayType="secondary"
							onClick={onPrev}
							small
							symbol="angle-left"
							title={Liferay.Language.get('previous-entry')}
						/>

						<ClayButtonWithIcon
							data-tooltip-align="bottom"
							data-tooltip-delay="200"
							disabled={page === totalCount}
							displayType="secondary"
							onClick={onNext}
							small
							symbol="angle-right"
							title={Liferay.Language.get('next-entry')}
						/>
					</ClayButtonGroup>
				</ClayTooltipProvider>
			</UpperToolbar.Group>

			{showFormView && (
				<UpperToolbar.Group>
					{permissions.delete && showDeleteButton && (
						<ClayTooltipProvider>
							<ClayButtonWithIcon
								className="mr-2"
								data-tooltip-align="bottom"
								data-tooltip-delay="200"
								displayType="secondary"
								onClick={onDelete}
								small
								symbol="trash"
								title={Liferay.Language.get('delete')}
							/>
						</ClayTooltipProvider>
					)}

					{permissions.update && showUpdateButton && (
						<ClayTooltipProvider>
							<ClayButtonWithIcon
								className="mr-2"
								data-tooltip-align="bottom"
								data-tooltip-delay="200"
								displayType="secondary"
								onClick={onEdit}
								small
								symbol="pencil"
								title={Liferay.Language.get('edit')}
							/>
						</ClayTooltipProvider>
					)}

					{additionalButtons}
				</UpperToolbar.Group>
			)}
		</UpperToolbar>
	);
}

export default withRouter(ViewEntryUpperToolbar);
