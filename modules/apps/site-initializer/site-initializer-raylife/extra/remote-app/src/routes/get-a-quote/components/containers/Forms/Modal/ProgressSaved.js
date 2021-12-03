import ClayIcon from '@clayui/icon';
import Modal from '../../../../../../common/components/modal';

import {LiferayService} from '../../../../../../common/services/liferay';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../common/services/liferay/storage';
import {clearExitAlert} from '../../../../../../common/utils/exitAlert';
import {createQuoteRetrieve} from '../../../../services/QuoteRetrieve';

const liferaySiteName = LiferayService.getLiferaySiteName();

const ProgressSaved = ({email, onClose, show}) => {
	const onSendLinkAndExit = async () => {
		const applicationId = Storage.getItem(STORAGE_KEYS.APPLICATION_ID);

		const raylifeProductName = JSON.parse(
			Storage.getItem(STORAGE_KEYS.PRODUCT)
		).productName;

		await createQuoteRetrieve({
			productName: raylifeProductName,
			quoteRetrieveLink: `${origin}${liferaySiteName}/get-a-quote?applicationId=${applicationId}`,
			retrieveEmail: email,
		});

		clearExitAlert();

		window.location.href = liferaySiteName;
	};

	return (
		<Modal
			footer={
				<div className="progress-saved-footer">
					<button className="btn btn-link link" onClick={onClose}>
						Continue Quote
					</button>

					<button
						className="btn btn-primary"
						onClick={onSendLinkAndExit}
					>
						Send Link &amp; Exit
					</button>
				</div>
			}
			onClose={onClose}
			show={show}
		>
			<div className="progress-saved-content">
				<div className="progress-saved-body">
					<div className="progress-saved-icon">
						<ClayIcon symbol="check" />
					</div>

					<div className="progress-saved-subtitle">
						Your progress is saved
					</div>

					<div className="progress-saved-description">
						We will send a link to
						<b>{` ${email}`}</b>. Use the link to pick up where you
						left off at any time.
					</div>
				</div>
			</div>
		</Modal>
	);
};

export default ProgressSaved;
