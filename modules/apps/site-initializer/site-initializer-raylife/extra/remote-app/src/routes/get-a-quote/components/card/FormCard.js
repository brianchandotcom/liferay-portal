import ClayCard from '@clayui/card';
import React, {useContext} from 'react';
import {DEVICES} from '../../../../common/utils/constants';
import {AppContext} from '../../context/AppContextProvider';

export default function FormCard({children, Footer = () => <></>}) {
	const {
		state: {
			dimensions: {deviceSize},
		},
	} = useContext(AppContext);

	return (
		<>
			<div className="col-12">
				<ClayCard className="d-flex flex-column m-auto px-3 px-lg-6 px-md-6 px-sm-3 py-5 py-lg-6 py-md-6 py-sm-5 rounded shadow-lg">
					{children}

					<Footer />
				</ClayCard>
			</div>

			{deviceSize === DEVICES.PHONE && <Footer rodape />}
		</>
	);
}
