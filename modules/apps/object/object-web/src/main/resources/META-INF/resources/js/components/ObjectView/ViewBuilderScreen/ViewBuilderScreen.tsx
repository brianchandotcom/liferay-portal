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

import {useModal} from '@clayui/modal';
import React, {useContext, useState} from 'react';

import {BuilderScreen} from '../BuilderScreen/BuilderScreen';
import ModalAddColumnsObjectCustomView from '../ModalAddColumns/ModalAddColumnsObjectCustomView';
import ViewContext from '../context';

const ViewBuilderScreen: React.FC<{}> = () => {
	const [
		{
			objectView: {objectViewColumns},
		},
	] = useContext(ViewContext);

	const [visibleModal, setVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => setVisibleModal(false),
	});

	return (
		<>
			<BuilderScreen
				emptyState={{
					buttonText: Liferay.Language.get('add-column'),
					description: Liferay.Language.get(
						'add-columns-to-start-creating-a-view'
					),
					title: Liferay.Language.get('no-columns-added-yet'),
				}}
				objectColumns={objectViewColumns ?? []}
				onVisibleModal={setVisibleModal}
				secondColumnHeader={Liferay.Language.get('column-label')}
				title={Liferay.Language.get('columns')}
			/>

			{visibleModal && (
				<ModalAddColumnsObjectCustomView
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

export default ViewBuilderScreen;
