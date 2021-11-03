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
import {useModal} from '@clayui/modal';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {useCallback, useState} from 'react';

import CreateLayoutPageTemplateEntryModal from './CreateLayoutPageTemplateEntryModal';

const CreateLayoutPageTemplateEntryButton = () => {
	const isMounted = useIsMounted();

	const [openModal, setOpenModal] = useState(false);
	const onClose = useCallback(() => {
		if (isMounted()) {
			setOpenModal(false);
		}
	}, [isMounted]);

	const {observer} = useModal({
		onClose,
	});

	return (
		<>
			<ClayButtonWithIcon
				className="btn btn-secondary"
				displayType="secondary"
				onClick={() => setOpenModal(true)}
				small
				symbol="page-template"
				title={Liferay.Language.get('create-page-template')}
				type="button"
			>
				{Liferay.Language.get('create-page-template')}
			</ClayButtonWithIcon>

			{openModal && (
				<CreateLayoutPageTemplateEntryModal
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

export {CreateLayoutPageTemplateEntryButton};
export default CreateLayoutPageTemplateEntryButton;
