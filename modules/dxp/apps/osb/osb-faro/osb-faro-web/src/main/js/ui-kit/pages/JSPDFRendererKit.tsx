import Card from 'shared/components/Card';
import DownloadPDFReport, {
	ReportContainer
} from 'shared/components/download-report/DownloadPDFReport';
import React from 'react';

const JSPDFRenderer = () => (
	<div>
		<div className='mb-3'>
			<DownloadPDFReport
				disabled={false}
				label='this is a label'
				subtitle='this is a subtitle'
				title='this is a title'
			/>
		</div>

		<Card reportContainer={ReportContainer.AcquisitionsCard}>
			<Card.Body>
				{'example of using ReportContainer.AcquisitionsCard container'}
			</Card.Body>
		</Card>

		<Card reportContainer={ReportContainer.ActiveIndividualsCard}>
			<Card.Body>
				{
					'example of using ReportContainer.ActiveIndividualsCard container'
				}
			</Card.Body>
		</Card>
	</div>
);

export default JSPDFRenderer;
