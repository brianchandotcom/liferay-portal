import React, { useMemo, useState, useEffect, useCallback, useContext } from 'react';

import { StoreContext } from './StoreContext.es';
import FolderViewer from './FolderViewer.es';
import Loading from './Loading.es';
import Breadcrumbs from './Breadcrumbs.es';
import ErrorMessage from './ErrorMessage.es';
import BaseContainer from './BaseContainer.es';
import AreaViewer from './areas/AreaViewer.es';
import Connector from '../utilities/data_connectors/Connector.es';

export function PartFinder(props) {

	const [initialized, setInitialized] = useState(false);
	const [page, updatePage] = useState('base');
	const { state, actions } = useContext(StoreContext);

	const connector = useMemo(() => {
		if(props.connectorSettings) {
			return new Connector(props.connectorSettings)
		}
		return null
	}, props.connectorSettings)

	const updateData = useCallback(() => {
		const filteredUrl = /^.*(folderId|areaId)=([0-9a-zA-Z\-]+)/.exec(props.history.location.search)
		const id = filteredUrl ? filteredUrl[2] : null;
		const queryParam = filteredUrl ? filteredUrl[1] : 'folderId'

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
	})

	function initialize() {
		actions.initialize({
			areasEndpoint: props.areasEndpoint,
			foldersEndpoint: props.foldersEndpoint,
			spritemap: props.spritemap,
			basename: props.basename || '/',
			basePathUrl: props.basePathUrl,
			history: props.history,
		});

		props.history.listen(e => {
			updateData()
		})

		updateData();
		setInitialized(true);
	}

	useEffect(() => {
		if (!initialized) {
			initialize();
		}
	});

	if(state.app.error) {
		return (<ErrorMessage />)
	}

	if(state.app.loading) {
		return (<Loading />)
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
