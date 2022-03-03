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
import {useModal} from '@clayui/modal';
import {useState} from 'react';
import AnalyticsCloudModal from '../../../components/AnalyticsCloudModal';
const AnalyticsCloud = () => {
	const [isVisible, setIsVisible] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setIsVisible(false),
	});

	return (
		<div>
			{isVisible && (
				<AnalyticsCloudModal observer={observer} onClose={onClose} />
			)}

			<h1>Analytics Cloud Activation</h1>

			<p className="text-primary" onClick={() => setIsVisible(true)}>
				Finish Activation
			</p>
		</div>
	);
};
export default AnalyticsCloud;
