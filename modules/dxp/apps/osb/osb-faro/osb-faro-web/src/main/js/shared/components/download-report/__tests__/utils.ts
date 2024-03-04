import moment from 'moment';
import {fontMapper, formatDate} from '../utils';
import {LanguageIds} from 'shared/util/constants';

describe('fontMapper', () => {
	it('returns true for Japanese text', () => {
		Object.keys(fontMapper).forEach(key => {
			const {test} = fontMapper[key];

			if (key === LanguageIds.Japanese) {
				expect(test('ライフレイ')).toBeTruthy();
				expect(test('プラットフォームのエクスペリエンス')).toBeTruthy();
				expect(test('分析クラウド')).toBeTruthy();
			}
		});
	});
});

describe('formatDate', () => {
	it('returns formatted date for PDF document', () => {
		expect(formatDate(moment(0))).toBe('1970-01-01');
	});
});
