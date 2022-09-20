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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {useModal} from '@clayui/modal';
import classNames from 'classnames';
import React, {useState} from 'react';

import ModalEditExternalReferenceCode from './ModalEditExternalReferenceCode';

export default function ManagementToolbar({
	externalReferenceCode: initialExternalReferenceCode,
	label,
	objectDefinitionId,
	system,
}: IProps) {
	const [externalReferenceCode, setExternalReferenceCode] = useState<string>(
		initialExternalReferenceCode
	);
	const [visibleModal, setVisibleModal] = useState<boolean>(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			<ClayManagementToolbar className="border-bottom">
				<ClayManagementToolbar.ItemList>
					<div className="border-right ml-sm-2 mr-3 pr-3">
						<h3 className="mb-0 text-truncate">{label}</h3>

						<strong
							className={classNames(
								system ? 'label-info' : 'label-warning',
								'label'
							)}
						>
							{system
								? Liferay.Language.get('system')
								: Liferay.Language.get('custom')}
						</strong>
					</div>

					<div>
						<div>
							<span className="text-secondary">
								{`${Liferay.Language.get('id')}:`}
							</span>

							<strong className="ml-2">
								{objectDefinitionId}
							</strong>
						</div>

						<div className="mt-1">
							<span className="text-secondary">
								{`${Liferay.Language.get('erc')}:`}
							</span>

							<strong className="ml-2">
								{externalReferenceCode}
							</strong>

							<span
								className="ml-3 text-secondary"
								title={Liferay.Language.get(
									'internal-key-to-refer-the-object-definition'
								)}
							>
								<ClayIcon symbol="question-circle" />
							</span>

							<ClayButton
								className="ml-3 p-0 text-secondary"
								displayType="unstyled"
								onClick={() => setVisibleModal(true)}
							>
								<ClayIcon symbol="pencil" />
							</ClayButton>
						</div>
					</div>
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

			{visibleModal && (
				<ModalEditExternalReferenceCode
					externalReferenceCode={externalReferenceCode}
					objectDefinitionId={objectDefinitionId}
					observer={observer}
					onClose={onClose}
					setExternalReferenceCode={setExternalReferenceCode}
				/>
			)}
		</>
	);
}

interface IProps {
	externalReferenceCode: string;
	label: string;
	objectDefinitionId: number;
	system: boolean;
}
