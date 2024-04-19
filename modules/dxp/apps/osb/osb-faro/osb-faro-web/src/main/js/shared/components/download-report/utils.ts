import FaroConstants, {
	LanguageIds,
	RangeKeyTimeRanges
} from 'shared/util/constants';
import html2canvas from 'html2canvas';
import JsPDF from 'jspdf';
import moment from 'moment';
import {buildOrderByFields} from 'shared/util/pagination';
import {DEFAULT_DATE_FORMAT} from 'shared/util/date';
import {DEFAULT_RANGE_SELECTORS} from 'shared/hooks/useQueryRangeSelectors';
import {INDIVIDUALS} from 'shared/util/router';
import {isJapaneseLang} from 'shared/util/lang';
import {TransformedContainer} from './DownloadPDFReport';
import {useParams} from 'react-router-dom';

const {pathThemeRoot} = FaroConstants;

const PRIMARY_COLOR = '#0B5FFF';

export function formatDate(date) {
	return moment(date).format(DEFAULT_DATE_FORMAT);
}

/**
 * Support extra fonts for PDF report.
 */

export const fontMapper: {
	[key: string]: {
		path: string;
		test: (value: string) => boolean;
		style: string[];
	};
} = {
	[LanguageIds.Japanese]: {
		path: `${pathThemeRoot}/fonts/noto-sans-jp-bold.ttf`,
		style: ['NotoSansJP', 'bold'],
		test: isJapaneseLang
	}
};

const setExtraFonts = (doc, value) => {
	Object.keys(fontMapper).forEach(key => {
		const {style, test} = fontMapper[key];

		if (test(value)) {
			doc.setFont(style[0], style[1]);
		}
	});
};

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

		Object.keys(fontMapper).forEach(key => {
			const {path, style} = fontMapper[key];

			doc.addFont(path, style[0], style[1]);
		});

		doc.setFillColor(241, 242, 245);
		doc.rect(0, 0, docWidth, docHeight, 'F');

		doc.setFillColor(255, 255, 255);
		doc.rect(0, 0, docWidth, headerHeight, 'F');

		doc.setFont('Helvetica', 'normal');
		doc.setTextColor(PRIMARY_COLOR);
		doc.setFontSize(8);
		doc.text('Analytics Cloud', paddingX, paddingY - 7);

		doc.setFont('Helvetica', 'bold');

		setExtraFonts(doc, title);

		doc.setTextColor('#000');
		doc.setFontSize(16);
		doc.text(title, paddingX, paddingY);

		doc.setTextColor('#6B6C7E');
		doc.setFontSize(8);

		if (url) {
			doc.setFontSize(7);
			doc.textWithLink(url, paddingX, paddingY + 5, {url});
		}

		doc.setFont('Helvetica', 'normal');

		if (subtitle) {
			setExtraFonts(doc, subtitle);

			doc.text(subtitle, paddingX, paddingY + (url ? 9 : 5));
		}

		doc.setFont('Helvetica', 'normal');

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

export enum CSVType {
	Individual = 'individual',
	Blog = 'blog',
	Document = 'document',
	Forms = 'forms',
	Journal = 'journal',
	Event = 'event',
	Page = 'page'
}

export function useDownloadCSV({
	assetId,
	assetType,
	type
}: {
	assetId?: string;
	assetType?: string;
	type: CSVType;
}) {
	const {channelId, groupId, title} = useParams();

	return initialRangeSelectors => {
		const rangeSelectors = initialRangeSelectors || DEFAULT_RANGE_SELECTORS;
		const searchParams = new URLSearchParams(location.search);

		const field = searchParams.get('field');
		const query = searchParams.get('query');
		const sortOrder = searchParams.get('sortOrder');

		let url = `/o/faro/main/${groupId}/reports/export/csv/${type}?channelId=${channelId}`;

		if (rangeSelectors.rangeKey === RangeKeyTimeRanges.CustomRange) {
			url += '&rangeKey=CUSTOM';
			url += `&fromDate=${formatDate(rangeSelectors?.rangeStart)}`;
			url += `&toDate=${formatDate(rangeSelectors?.rangeEnd)}`;
		} else {
			url += `&rangeKey=${rangeSelectors.rangeKey}`;
		}

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

		return url;
	};
}

export const MAX_CSV_ENTRIES = 10000;
