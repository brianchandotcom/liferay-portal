/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Formik} from 'formik';
import SetupLiferayExperienceCloudPage from './components/SetupLXCPage';
import getInitialLxcAdmins from './utils/getInitialLxcAdmins';

const SetupLiferayExperienceCloudForm = (props) => {
	return (
		<Formik
			initialValues={{
				lxc: {
					admins: [getInitialLxcAdmins()],
					incidentManagementEmail: '',
					incidentManagementFullName: '',
					primaryRegion: '',
					projectId: '',
				},
			}}
			validadeOnChange
		>
			{(formikProps) => (
				<SetupLiferayExperienceCloudPage {...props} {...formikProps} />
			)}
		</Formik>
	);
};

export default SetupLiferayExperienceCloudForm;
