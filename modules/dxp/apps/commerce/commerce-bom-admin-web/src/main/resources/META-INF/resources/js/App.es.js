import React, {
    useState,
    useContext,
    useEffect
} from 'react';

import StoreContext from './components/StoreContext.es';

import PictureBox from './components/PictureBox.es';
import DetailsBox from './components/DetailsBox.es';
import ErrorMessage from './components/ErrorMessage.es';
import Icon from './components/utilities/Icon.es';

function App(props) {
	const { state, actions } = useContext(StoreContext);
	const [ dataFetched, updateDataFetchedStatus ] = useState(false)

	useEffect(() => {
		if (!state.app.initialized) {
            actions.initializeAppData({
                spritemap: props.spritemap,
                areaApiUrl: props.areaApiUrl,
				productApiUrl: props.productApiUrl,
				areaId: props.areaId
            });
        }

        if(
			state.app.initialized && 
			!dataFetched	
		) {
			actions.getArea(state.app.areaApiUrl, state.area.id);
			updateDataFetchedStatus(true)
        }
	});

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
			{state.app.error && 
				<ErrorMessage 
					closeIcon={<Icon spritemap={state.app.spritemap} symbol={'close'}/>}
					message={state.app.error}
					onClose={actions.dismissError}
				/>
			}
		</div>
	);
}

export default App;
