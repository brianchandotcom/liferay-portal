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

import i18n from '../../../../common/I18n';
import CardWithSla from './components/CardWithSla';
import CardWithoutSla from './components/CardWithoutSla';
import useGetSlaData from './hooks/useGetSlaData';

const SlaCard = ({project}) => {
	const {setSlaSelected, slaData, slaSelected} = useGetSlaData(project);

	return (
		<div className="cp-sla-container position-absolute">
			<h5 className="mb-4">{i18n.translate('support-level')}</h5>

			{slaData?.length ? (
				<CardWithSla
					setSlaSelected={setSlaSelected}
					slaData={slaData}
					slaSelected={slaSelected}
				/>
			) : (
				<CardWithoutSla />
			)}
		</div>
	);
};

export default SlaCard;
