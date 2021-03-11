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

import React, {useContext, useEffect, useState} from 'react';

import DetailsBox from './components/DetailsBox.es';
import ErrorMessage from './components/ErrorMessage.es';
import PictureBox from './components/PictureBox.es';
import StoreContext from './components/StoreContext.es';
import Icon from './components/utilities/Icon.es';

function App(props) {
	const {actions, state} = useContext(StoreContext);
	const [dataFetched, updateDataFetchedStatus] = useState(false);

	useEffect(() => {
		if (!state.app.initialized) {
			actions.initializeAppData({
				areaApiUrl: props.areaApiUrl,
				areaId: props.areaId,
				productApiUrl: props.productApiUrl,
				spritemap: props.spritemap,
			});
		}

		if (state.app.initialized && !dataFetched) {
			actions.getArea(state.app.areaApiUrl, state.area.id);
			updateDataFetchedStatus(true);
		}
	}, [
		state.app.initialized,
		state.app.areaApiUrl,
		state.area.id,
		dataFetched,
		actions,
		props.spritemap,
		props.areaApiUrl,
		props.productApiUrl,
		props.areaId,
	]);

	return (
		<div className="bom-admin-container container pt-3">
			<div className="row">
				<div className="col-12 col-xl-8">
					<PictureBox />
				</div>
				<div className="col-12 col-xl-4">
					<DetailsBox />
				</div>
			</div>
			{state.app.error && (
				<ErrorMessage
					closeIcon={
						<Icon spritemap={state.app.spritemap} symbol="close" />
					}
					message={state.app.error}
					onClose={actions.dismissError}
				/>
			)}
		</div>
	);
}

export default App;
