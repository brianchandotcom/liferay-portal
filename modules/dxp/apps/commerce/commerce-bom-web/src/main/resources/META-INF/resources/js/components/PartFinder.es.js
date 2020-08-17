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

import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import Connector from '../utilities/data_connectors/Connector.es';
import BaseContainer from './BaseContainer.es';
import Breadcrumbs from './Breadcrumbs.es';
import ErrorMessage from './ErrorMessage.es';
import FolderViewer from './FolderViewer.es';
import Loading from './Loading.es';
import {StoreContext} from './StoreContext.es';
import AreaViewer from './areas/AreaViewer.es';

export function PartFinder(props) {
	const [initialized, setInitialized] = useState(false);
	const [page, updatePage] = useState('base');
	const {actions, state} = useContext(StoreContext);

	useMemo(() => {
		if (props.connectorSettings) {
			return new Connector(props.connectorSettings);
		}

		return null;
	}, [props.connectorSettings]);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const updateData = useCallback(() => {
		const filteredUrl = /^.*(folderId|areaId)=([0-9a-zA-Z-]+)/.exec(
			props.history.location.search
		);
		const id = filteredUrl ? filteredUrl[2] : null;
		const queryParam = filteredUrl ? filteredUrl[1] : 'folderId';

		switch (queryParam) {
			case 'folderId':
				actions.getFolder(props.foldersEndpoint, id);
				updatePage('folder');
				break;
			case 'areaId':
				actions.getArea(props.areasEndpoint, id);
				updatePage('area');
				break;
			default:
				break;
		}
	});

	function initialize() {
		actions.initialize({
			areasEndpoint: props.areasEndpoint,
			basePathUrl: props.basePathUrl,
			basename: props.basename || '/',
			foldersEndpoint: props.foldersEndpoint,
			history: props.history,
			spritemap: props.spritemap,
		});

		props.history.listen(() => {
			updateData();
		});

		updateData();
		setInitialized(true);
	}

	useEffect(() => {
		if (!initialized) {
			initialize();
		}
	});

	if (state.app.error) {
		return <ErrorMessage />;
	}

	if (state.app.loading) {
		return <Loading />;
	}

	return (
		<div className="content">
			<Breadcrumbs data={state.app.breadcrumbs} />
			{page === 'base' && <BaseContainer />}
			{page === 'area' && <AreaViewer />}
			{page === 'folder' && <FolderViewer />}
		</div>
	);
}

export default PartFinder;
