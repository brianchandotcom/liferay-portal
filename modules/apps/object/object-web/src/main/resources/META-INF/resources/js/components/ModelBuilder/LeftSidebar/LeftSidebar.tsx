/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayPanel from '@clayui/panel';
import {
	CustomVerticalBar,
	ManagementToolbarSearch,
} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import LeftSidebarTreeView from './LeftSidebarTreeView';

interface LeftSidebarProps {
	setShowModal: (value: React.SetStateAction<ModelBuilderModals>) => void;
}

export default function LeftSidebar({setShowModal}: LeftSidebarProps) {
	const [query, setQuery] = useState('');
	const [
		{isLoadingObjectFolder, leftSidebarItems},
	] = useObjectFolderContext();

	return (
		<CustomVerticalBar
			defaultActive="objectsModelBuilderLeftSidebar"
			panelWidth={300}
			position="left"
			resize={false}
			triggerSideBarAnimation={true}
			verticalBarItems={[
				{
					title: 'objectsModelBuilderLeftSidebar',
				},
			]}
		>
			<div className="lfr-objects__model-builder-left-sidebar">
				<ClayButton
					className="lfr-objects__model-builder-left-sidebar-body-create-new-object-button"
					onClick={() =>
						setShowModal((previousState: ModelBuilderModals) => ({
							...previousState,
							addObjectDefinition: true,
						}))
					}
				>
					{Liferay.Language.get('create-new-object')}
				</ClayButton>

				<ManagementToolbarSearch
					query={query}
					setQuery={(searchTerm) => setQuery(searchTerm)}
				/>

				{!isLoadingObjectFolder ? (
					<>
						{!!leftSidebarItems.length && (
							<>
								<LeftSidebarTreeView query={query} />

								<ClayPanel
									className="lfr-objects__model-builder-left-sidebar-body-panel"
									collapsable
									defaultExpanded
									displayTitle={Liferay.Language.get(
										'other-folders'
									)}
									displayType="unstyled"
									showCollapseIcon={true}
								>
									<ClayPanel.Body>
										<LeftSidebarTreeView
											query={query}
											showActions
										/>
									</ClayPanel.Body>
								</ClayPanel>
							</>
						)}
					</>
				) : (
					<div className="lfr-objects__model-builder-left-sidebar-loading">
						<span
							aria-hidden="true"
							className="loading-animation loading-animation-secondary loading-animation-sm"
						></span>
					</div>
				)}
			</div>
		</CustomVerticalBar>
	);
}
