import ClayLayout from '@clayui/layout';
import React from 'react';
import Toolbar from './Toolbar';
import {Routes, toRoute} from 'shared/util/router';
import {useParams} from 'react-router-dom';

const WizardPage = ({children}) => {
	const {groupId} = useParams();

	return (
		<div className='wizard-page'>
			<Toolbar
				backURL={{
					label: Liferay.Language.get('data-sources'),
					url: toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
						groupId
					})
				}}
			/>

			<ClayLayout.Container fluid>
				<ClayLayout.Row>
					<ClayLayout.Col md={6} sm={12}>
						<div className='wizard-page__content-col'>
							{children}
						</div>
					</ClayLayout.Col>
					<ClayLayout.Col size={6}>
						<div className='wizard-page__onboarding-col'>
							<div className='wizard-page__onboarding-image' />
						</div>
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.Container>
		</div>
	);
};

export default WizardPage;
