import ClayButton from '@clayui/button';
import DateRangeInput, {DateRange} from 'shared/components/DateRangeInput';
import Loading, {Align} from 'shared/components/Loading';
import Modal from 'shared/components/modal';
import moment from 'moment';
import Promise from 'metal-promise';
import React, {useState} from 'react';
import {downloadDataAsFile} from 'shared/util/util';

interface IExportLogModalProps {
	description: string;
	fileName: string;
	onClose: () => void;
	onSubmit: ({
		fromDate,
		toDate
	}: {
		fromDate: string;
		toDate: string;
	}) => typeof Promise;
	title: string;
}

const ExportLogModal: React.FC<IExportLogModalProps> = ({
	description,
	fileName,
	onClose,
	onSubmit,
	title
}) => {
	const [dateRange, setDateRange] = useState<DateRange>({
		end: null,
		start: null
	});

	const [loading, setLoading] = useState<boolean>(false);

	const isValid = (): boolean =>
		moment(dateRange.end).isValid() && moment(dateRange.start).isValid();

	const {end: toDate, start: fromDate} = dateRange;

	return (
		<Modal className='export-log-modal-root'>
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body>
				<p className='text-secondary'>{description}</p>

				<h4>{Liferay.Language.get('request-date-range')}</h4>

				<div className='d-flex'>
					<DateRangeInput onChange={setDateRange} value={dateRange} />

					<ClayButton
						className='button-root download'
						disabled={!isValid()}
						displayType='primary'
						onClick={() => {
							setLoading(true);

							onSubmit({fromDate, toDate})
								.then((data: string) => {
									downloadDataAsFile({
										data,
										name: fileName,
										type: 'text/csv'
									});

									setLoading(false);

									onClose();
								})
								.catch(() => {
									setLoading(false);
								});
						}}
					>
						{loading && <Loading align={Align.Left} />}

						{Liferay.Language.get('download')}
					</ClayButton>
				</div>
			</Modal.Body>
		</Modal>
	);
};

export default ExportLogModal;
