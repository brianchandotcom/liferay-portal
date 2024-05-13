import {fontMapper, JSPDFExtension, PosX, Size, Weight} from '../jsPDF';
import {LanguageIds} from 'shared/util/constants';

describe('fontMapper', () => {
	it('test fontMapper function', () => {
		Object.keys(fontMapper).forEach(key => {
			const {test} = fontMapper[key];

			if (key === LanguageIds.Japanese) {
				expect(test('ライフレイ')).toBeTruthy();
				expect(test('プラットフォームのエクスペリエンス')).toBeTruthy();
				expect(test('分析クラウド')).toBeTruthy();
				expect(test('My Awesome Page')).toBeFalsy();
			}

			if (key === LanguageIds.English) {
				expect(test('ライフレイ')).toBeFalsy();
				expect(test('プラットフォームのエクスペリエンス')).toBeFalsy();
				expect(test('分析クラウド')).toBeFalsy();
				expect(test('My Awesome Page')).toBeTruthy();
			}
		});
	});
});

describe('JSPDFExtension', () => {
	it('should create a new instance of JSPDFExtension', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		expect(jsPDFExtension).toBeInstanceOf(JSPDFExtension);
	});

	it('should add text to the textList', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		jsPDFExtension.addText({
			color: 'black',
			size: Size.Small,
			value: 'test',
			weight: Weight.Bold
		});

		expect(jsPDFExtension.textList).toHaveLength(1);
	});

	it('should add float text to the floatTextList', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		jsPDFExtension.addFloatText({
			color: 'black',
			posX: PosX.Left,
			posY: 10,
			size: Size.Small,
			value: 'test',
			weight: Weight.Bold
		});

		expect(jsPDFExtension.floatTextList).toHaveLength(1);
	});

	it('should truncate text if it is too long', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});
		const text = 'This is a very long text that needs to be truncated';
		const truncatedText = jsPDFExtension.truncateText(text);

		expect(truncatedText).toBe(
			'This is a very long text that needs to be trunca...'
		);
	});

	it('should get the name of the PDF file', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});
		const fileName = jsPDFExtension.getName();

		expect(fileName).toBe('analytics-cloud-test-1970-01-01.pdf');
	});

	it('should get the X position of the text', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});
		const posX = jsPDFExtension.getPosX(PosX.Left);

		expect(posX).toBe(10);
	});

	it('should set the extra font', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		jsPDFExtension.setExtraFont('日本語');

		expect(jsPDFExtension.doc.getFontList()).toHaveProperty('NotoSansJP');
	});

	it('should set the font', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		jsPDFExtension.setFont({
			color: 'black',
			size: Size.Small,
			value: 'test',
			weight: Weight.Bold
		});

		expect(jsPDFExtension.doc.getFont()).toEqual(
			expect.objectContaining({fontName: 'helvetica'})
		);
		expect(jsPDFExtension.doc.getFontSize()).toBe(8);
		expect(jsPDFExtension.doc.getTextColor()).toBe('#000000');
	});

	it('should get the data for the PDF', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		jsPDFExtension.addText({
			color: 'black',
			size: Size.Small,
			value: 'test',
			weight: Weight.Bold
		});

		const data = jsPDFExtension.getData();

		expect(data).toHaveLength(1);
	});

	it('should render the containers', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});
		const headerHeight = 100;

		jsPDFExtension.renderContainers(headerHeight);

		expect(jsPDFExtension.doc.getNumberOfPages()).toBe(1);
	});

	it('should render the PDF', () => {
		const containers = [];
		const fontFamily = 'Helvetica';
		const name = 'test';
		const jsPDFExtension = new JSPDFExtension({
			containers,
			date: new Date(0),
			fontFamily,
			name
		});

		jsPDFExtension.addText({
			color: 'black',
			size: Size.Small,
			value: 'test',
			weight: Weight.Bold
		});

		// Mock the save function to prevent downloading the PDF
		jsPDFExtension.doc.save = jest.fn();

		jsPDFExtension.render();

		// Assert that the save function was called
		expect(jsPDFExtension.doc.save).toHaveBeenCalledTimes(1);

		expect(jsPDFExtension.doc.output('datauristring')).toBeString();
	});
});
