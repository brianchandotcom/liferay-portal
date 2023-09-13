/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {getLocalizableLabel} from '@liferay/object-js-components-web';
import React from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface ObjectFolderCardHeaderProps {
	externalReferenceCode?: string;
	items: IItem[];
	label?: LocalizedValue<string>;
	modelBuilderURL: string;
	name?: string;
}

export default function ObjectFolderCardHeader({
	externalReferenceCode,
	items,
	label,
	modelBuilderURL,
	name,
}: ObjectFolderCardHeaderProps) {
	return (
		<div className="lfr__object-web-view-object-definitions-card-header">
			<div>
				<div className="d-flex lfr__object-web-view-object-definitions-title-kebab">
					<span className="lfr__object-web-view-object-definitions-title mb-0">
						{getLocalizableLabel(defaultLanguageId, label, name)}
					</span>

					<ClayDropDownWithItems
						className="lfr__object-web-view-object-definitions-actions"
						items={items}
						trigger={
							<ClayButton
								aria-label={Liferay.Language.get(
									'folder-actions'
								)}
								className="component-action"
								displayType="unstyled"
								monospaced
							>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					/>
				</div>

				<div className="mt-1">
					<span className="text-secondary">
						{`${Liferay.Language.get('erc')}:`}
					</span>

					<strong className="ml-2">{externalReferenceCode}</strong>

					<span
						className="ml-3 text-secondary"
						title={Liferay.Language.get(
							'unique-key-for-referencing-the-object-folder'
						)}
					>
						<ClayIcon symbol="question-circle" />
					</span>
				</div>
			</div>

			<ClayButton
				aria-label={Liferay.Language.get('view-in-model-builder')}
				className="lfr__object-web-view-object-definitions-view-in-model-builder-button"
				displayType="secondary"
				onClick={() => {
					window.location.href =
						`${modelBuilderURL}` + `&objectFolderName=${name}`;
				}}
			>
				{Liferay.Language.get('view-in-model-builder')}
			</ClayButton>
		</div>
	);
}
