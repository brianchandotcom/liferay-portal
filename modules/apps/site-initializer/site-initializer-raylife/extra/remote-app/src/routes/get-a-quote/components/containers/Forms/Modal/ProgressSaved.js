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

const ProgressSaved = ({email, onClose, setError, show}) => {
	const onSendLinkAndExit = async () => {
		try {
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
		} catch (error) {
			setError('Unable to save your information. Please try again.');
			onClose();
		}
	};

	return (
		<Modal
			footer={
				<div className="align-items-center d-flex flex-row justify-content-between ml-2 mr-1">
					<button
						className="btn btn-link link text-link-md text-neutral-7 text-small-caps"
						onClick={onClose}
					>
						Continue Quote
					</button>

					<button
						className="btn btn-primary text-link-md text-small-caps"
						onClick={onSendLinkAndExit}
					>
						Send Link &amp; Exit
					</button>
				</div>
			}
			onClose={onClose}
			show={show}
		>
			<div className="align-items-center d-flex flex-column justify-content-between mb-9 mt-5 progress-saved-content">
				<div className="align-items-center d-flex flex-column progress-saved-body">
					<div className="align-items-center bg-success d-flex flex-shrink-0 justify-content-center progress-saved-icon rounded-circle">
						<ClayIcon symbol="check" />
					</div>

					<h2 className="font-weight-bolder">
						Your progress is saved
					</h2>

					<div className="font-weight-normal pt-1 text-center text-neutral-8 text-paragraph">
						We will send a link to
						<b>{` ${email}`}</b>. <br />
						Use the link to pick up where you left off at any time.
					</div>
				</div>
			</div>
		</Modal>
	);
};

export default ProgressSaved;
