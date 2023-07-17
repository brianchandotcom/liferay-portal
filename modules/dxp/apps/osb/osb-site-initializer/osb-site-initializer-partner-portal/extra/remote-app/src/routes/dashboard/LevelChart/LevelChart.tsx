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

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';

import Container from '../../../common/components/dashboard/components/Container';
import ClayIconProvider from '../../../common/components/dashboard/utils/ClayIconProvider';
import PartnershipLevel from './components/PartnershipLevel';
import useGetAccountInformation from './hooks/useAccountInformation';

const LevelChart = () => {
	const {
		aRRResults,
		account,
		checkedProperties,
		headcount,
		loading,
		partnerLevel,
	} = useGetAccountInformation();

	const BuildPartnershipLevel = () => {
		if (loading) {
			return <ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!account || !partnerLevel) {
			return (
				<ClayAlert
					className="mb-8 mt-8 mx-auto text-center w-50"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			);
		}

		return (
			<PartnershipLevel
				aRRResults={aRRResults}
				account={account}
				checkedProperties={checkedProperties}
				headcount={headcount}
				partnerLevel={partnerLevel}
			/>
		);
	};

	return (
		<ClayIconProvider>
			<Container title="Partnership Level">
				<BuildPartnershipLevel />
			</Container>
		</ClayIconProvider>
	);
};

export default LevelChart;
