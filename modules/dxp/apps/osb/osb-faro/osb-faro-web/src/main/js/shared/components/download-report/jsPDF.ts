import FaroConstants, {LanguageIds} from 'shared/util/constants';
import JSPDF from 'jspdf';
import {formatDate} from './utils';
import {isJapaneseLang} from 'shared/util/lang';

const {pathThemeRoot} = FaroConstants;

export enum Size {
	Small = 'small',
	Medium = 'medium',
	Large = 'large'
}

export enum Weight {
	Normal = 'normal',
	Bold = 'bold'
}

export enum PosX {
	Left = 'left',
	Right = 'right'
}

type Text = {
	color: string;
	size: Size;
	truncateText?: boolean;
	url?: string;
	value?: string;
	weight: Weight;
};

type Data = {
	text: Text;
	x: number;
	y: number;
};

interface FloatText extends Text {
	posY: number;
	posX: PosX;
}

export type JSPDFExtensionContainer = {
	containerElement: HTMLElement;
	imageData: string;
	layout: 1 | 2 | 3;
};

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

export class JSPDFExtension {
	config: {
		date: Date;
		container: {
			list: JSPDFExtensionContainer[];
			padding: number;
		};
		fontFamily: string;
		fontSize: {
			large: {
				lineHeight: number;
				size: number;
			};
			medium: {
				lineHeight: number;
				size: number;
			};
			small: {
				lineHeight: number;
				size: number;
			};
		};
		name: string;
		paddingX: number;
		paddingY: number;
		pageHeight: number;
		pageWidth: number;
		/**
		 * jsPDF does not render a text on padding Y is equal 0
		 * we need to add a safe padding Y at first to fix it.
		 */
		safePaddingY: number;
		spacingBetweenBreakLines: number;
		spacingBetweenTexts: number;
	};

	data: any[];
	doc: JSPDF;
	textList: Text[];
	floatTextList: FloatText[];

	constructor({containers, date = new Date(), fontFamily, name}) {
		this.doc = new JSPDF();

		this.config = {
			container: {
				list: containers,
				padding: 2
			},
			date,
			fontFamily,
			fontSize: {
				large: {lineHeight: 4.5, size: 18},
				medium: {lineHeight: 3.5, size: 14},
				small: {lineHeight: 2.5, size: 8}
			},
			name,
			paddingX: 10,
			paddingY: 5,
			pageHeight: this.doc.internal.pageSize.getHeight(),
			pageWidth: this.doc.internal.pageSize.getWidth(),
			safePaddingY: 5,
			spacingBetweenBreakLines: 2,
			spacingBetweenTexts: 5
		};

		this.textList = [];
		this.floatTextList = [];

		/**
		 * Configure PDF to support extra fonts
		 */

		Object.keys(fontMapper).forEach(key => {
			const {path, style} = fontMapper[key];

			this.doc.addFont(path, style[0], style[1]);
		});
	}

	addText(text: Text) {
		if (!text.value) return;

		this.textList.push(text);
	}

	addFloatText(text: FloatText) {
		if (!text.value) return;

		this.floatTextList.push(text);
	}

	truncateText(text: string) {
		if (text) return `${text.substring(0, text.length - 3)}...`;
	}

	getName() {
		return `analytics-cloud-${this.config.name
			.slice(0, 100)
			.replace(' ', '-')
			.toLowerCase()}-${formatDate(this.config.date)}.pdf`;
	}

	getPosX(posX) {
		if (posX === 'right') {
			return this.config.pageWidth - this.config.paddingX;
		}

		return this.config.paddingX;
	}

	setExtraFont(value: string) {
		Object.keys(fontMapper).forEach(key => {
			const {style, test} = fontMapper[key];

			if (test(value)) {
				this.doc.setFont(style[0], style[1]);
			}
		});
	}

	setFont(text: Text | FloatText) {
		this.doc.setFont(this.config.fontFamily, text.weight);
		this.doc.setFontSize(this.config.fontSize[text.size].size);
		this.doc.setTextColor(text.color);

		this.setExtraFont(text.value);
	}

	getData() {
		const paddingY = this.config.paddingY + this.config.safePaddingY;
		const data: Data[] = [];

		let prevLineHeight = 0;
		let posY = 0;

		this.textList.forEach((text, index) => {
			this.setFont(text);

			let textValue = text.value;

			posY =
				paddingY +
				this.config.fontSize[text.size].lineHeight +
				prevLineHeight +
				this.config.spacingBetweenTexts * index;
			const lines = this.doc.splitTextToSize(
				text.value,
				this.config.pageWidth - this.config.paddingX * 2
			);

			if (lines.length > 1 && text.truncateText) {
				textValue = this.truncateText(lines[0]);
			}

			if (lines.length > 1 && !text.truncateText) {
				let linePosY = 0;

				lines.forEach(line => {
					data.push({
						text: {
							...text,
							value: line
						},
						x: this.config.paddingX,
						y: posY + linePosY
					});

					linePosY =
						linePosY +
						this.config.fontSize[text.size].lineHeight +
						this.config.spacingBetweenBreakLines;
				});

				prevLineHeight =
					prevLineHeight +
					lines.length * this.config.fontSize[text.size].lineHeight +
					lines.length * this.config.spacingBetweenBreakLines;
			} else {
				data.push({
					text: {
						...text,
						value: textValue
					},
					x: this.config.paddingX,
					y: posY
				});

				prevLineHeight =
					prevLineHeight + this.config.fontSize[text.size].lineHeight;
			}
		});

		return data;
	}

	renderContainers(headerHeight) {
		let containerY = headerHeight + 2;
		let previousLayout = null;
		let previousContainerY = containerY;
		let previousContainerX = this.config.container.padding;

		this.config.container.list.forEach(
			({containerElement, imageData, layout}) => {
				const containerWidth =
					(this.config.pageWidth -
						(layout + 1) * this.config.container.padding) /
					layout;
				const containerHeight = Math.round(
					(containerElement.clientHeight * containerWidth) /
						containerElement.clientWidth
				);

				let containerX = this.config.container.padding;

				if (previousLayout && previousLayout !== 1 && layout !== 1) {
					if (
						previousContainerX + containerWidth <=
						this.config.pageWidth
					) {
						containerX = previousContainerX;
						containerY = previousContainerY;
					} else {
						previousContainerX = this.config.container.padding;
					}
				} else {
					previousContainerX = this.config.container.padding;
				}

				if (containerY + containerHeight > this.config.pageHeight) {
					containerY = this.config.container.padding;

					this.doc.addPage();

					this.doc.setFillColor(241, 242, 245);
					this.doc.rect(
						0,
						0,
						this.config.pageWidth,
						this.config.pageHeight,
						'F'
					);

					this.doc.addImage(
						imageData,
						'JPEG',
						containerX,
						containerY,
						containerWidth,
						containerHeight
					);
				} else {
					this.doc.addImage(
						imageData,
						'JPEG',
						containerX,
						containerY,
						containerWidth,
						containerHeight
					);
				}

				previousContainerX =
					previousContainerX +
					containerWidth +
					this.config.container.padding;
				previousContainerY = containerY;
				containerY =
					containerY +
					containerHeight +
					this.config.container.padding;
				previousLayout = layout;
			}
		);
	}

	render() {
		const data = this.getData();

		const headerHeight =
			data[data.length - 1].y +
			this.config.paddingY +
			this.config.safePaddingY;

		/**
		 * Set Background Page
		 */
		this.doc.setFillColor(241, 242, 245);
		this.doc.rect(0, 0, this.config.pageWidth, this.config.pageHeight, 'F');

		/**
		 * Set Background Header
		 */
		this.doc.setFillColor(255, 255, 255);
		this.doc.rect(0, 0, this.config.pageWidth, headerHeight, 'F');

		/**
		 * Render Float Texts
		 */
		this.floatTextList.forEach(text => {
			this.setFont(text);

			this.doc.textWithLink(
				text.value,
				this.getPosX(text.posX),
				text.posY + this.config.fontSize[text.size].lineHeight,
				{
					align: text.posX,
					url: text.url
				}
			);
		});

		/**
		 * Render Texts
		 */
		data.forEach(({text, x, y}) => {
			this.setFont(text);

			this.doc.textWithLink(text.value, x, y, {
				url: text.url
			});
		});

		/**
		 * Render Containers
		 */
		this.renderContainers(headerHeight);

		/**
		 * Save PDF
		 */
		this.doc.save(this.getName());
	}
}
