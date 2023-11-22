import html2canvas from 'html2canvas';
import JsPDF from 'jspdf';
import moment from 'moment';
import {buildOrderByFields} from 'shared/util/pagination';
import {DEFAULT_DATE_FORMAT} from 'shared/util/date';
import {INDIVIDUALS} from 'shared/util/router';
import {TransformedContainer} from './DownloadPDFReport';
import {useParams} from 'react-router-dom';

const PRIMARY_COLOR = '#0B5FFF';

export function formatDate(date) {
	return moment(date).format(DEFAULT_DATE_FORMAT);
}

export function generateReport({
	containers,
	subtitle,
	title,
	url
}: {
	containers: TransformedContainer[];
	subtitle: string;
	title: string;
	url?: string;
}): Promise<any> {
	const doc = new JsPDF();

	const docName = `analytics-cloud-${title
		.replace(' ', '-')
		.toLowerCase()}-${formatDate(new Date())}.pdf`;

	const headerHeight = 30;
	const paddingX = 10;
	const paddingY = 16;
	const docWidth = doc.internal.pageSize.getWidth();
	const docHeight = doc.internal.pageSize.getHeight();
	const padding = 2;
	const containerArr = [];
	const promises: Promise<void>[] = [];

	containers.map(({id, layout}) => {
		const containerElement = document.querySelector(
			`#${id}`
		) as HTMLElement;

		if (!containerElement) {
			throw new Error(`container not found! ID: ${id}`);
		}

		const promise = html2canvas(containerElement, {
			backgroundColor: '#F1F2F5',
			logging: false
		}).then(canvas => {
			const imageData = canvas.toDataURL('image/jpeg', 1.0);

			containerArr.push({containerElement, imageData, layout});
		});

		promises.push(promise);
	});

	return Promise.all(promises).then(() => {
		// Generate PDF Header

		doc.setFillColor(241, 242, 245);
		doc.rect(0, 0, docWidth, docHeight, 'F');

		doc.setFillColor(255, 255, 255);
		doc.rect(0, 0, docWidth, headerHeight, 'F');

		doc.setFont('Helvetica', 'normal');
		doc.setTextColor(PRIMARY_COLOR);
		doc.setFontSize(8);
		doc.text('Analytics Cloud', paddingX, paddingY - 7);

		doc.setFont('Helvetica', 'bold');
		doc.setTextColor('#000');
		doc.setFontSize(16);
		doc.text(title, paddingX, paddingY);

		doc.setFont('Helvetica', 'normal');
		doc.setTextColor('#6B6C7E');
		doc.setFontSize(8);

		if (url) {
			doc.setFontSize(7);
			doc.textWithLink(url, paddingX, paddingY + 5, {url});
		}

		if (subtitle) {
			doc.text(subtitle, paddingX, paddingY + (url ? 9 : 5));
		}

		doc.setFontSize(8);
		doc.setTextColor(PRIMARY_COLOR);
		doc.textWithLink(
			Liferay.Language.get('access-workspace'),
			docWidth - paddingX - 25,
			paddingY - 7,
			{url: window.location.href}
		);

		// Generate PDF containers

		let containerY = headerHeight + 2;
		let previousLayout = null;
		let previousContainerY = containerY;
		let previousContainerX = padding;

		containerArr.forEach(({containerElement, imageData, layout}) => {
			const containerWidth = (docWidth - (layout + 1) * padding) / layout;
			const containerHeight = Math.round(
				(containerElement.clientHeight * containerWidth) /
					containerElement.clientWidth
			);

			let containerX = padding;

			if (previousLayout && previousLayout !== 1 && layout !== 1) {
				if (previousContainerX + containerWidth <= docWidth) {
					containerX = previousContainerX;
					containerY = previousContainerY;
				} else {
					previousContainerX = padding;
				}
			} else {
				previousContainerX = padding;
			}

			if (containerY + containerHeight > docHeight) {
				containerY = padding;

				doc.addPage();

				doc.setFillColor(241, 242, 245);
				doc.rect(0, 0, docWidth, docHeight, 'F');

				doc.addImage(
					imageData,
					'JPEG',
					containerX,
					containerY,
					containerWidth,
					containerHeight
				);
			} else {
				doc.addImage(
					imageData,
					'JPEG',
					containerX,
					containerY,
					containerWidth,
					containerHeight
				);
			}

			previousContainerX = previousContainerX + containerWidth + padding;
			previousContainerY = containerY;
			containerY = containerY + containerHeight + padding;
			previousLayout = layout;
		});

		doc.save(docName);
	});
}

export function useDownloadCSV({
	assetId,
	assetType,
	type
}: {
	assetId?: string;
	assetType?: string;
	type: string;
}) {
	const {channelId, groupId, title} = useParams();

	return {
		onClick: dateRange => {
			const searchParams = new URLSearchParams(location.search);

			const field = searchParams.get('field');
			const query = searchParams.get('query');
			const rangeKey = searchParams.get('rangeKey');
			const sortOrder = searchParams.get('sortOrder');

			const a = document.createElement('a');

			let url = `/o/faro/main/${groupId}/reports/export/csv/${type}?channelId=${channelId}&fromDate=${formatDate(
				dateRange?.start
			)}&toDate=${formatDate(dateRange?.end)}`;

			if (assetId) {
				url += `&assetId=${encodeURIComponent(assetId)}`;
			}

			if (title) {
				url += `&assetTitle=${title}`;
			}

			if (assetType) {
				url += `&assetType=${assetType}`;
			}

			if (field && sortOrder) {
				const orderByFields = JSON.stringify(
					buildOrderByFields({field, sortOrder}, INDIVIDUALS)
				);

				url += `&orderByFields=${encodeURIComponent(orderByFields)}`;
			}

			if (query) {
				url += `&query=${query}`;
			}

			if (dateRange?.end && dateRange?.start) {
				url += '&rangeKey=CUSTOM';
			} else if (rangeKey) {
				url += `&rangeKey=${rangeKey}`;
			}

			a.href = url;

			a.click();
		}
	};
}
