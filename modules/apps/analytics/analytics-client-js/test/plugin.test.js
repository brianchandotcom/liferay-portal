import AnalyticsClient from '../src/analytics';
import {assert} from 'chai';

let Analytics = AnalyticsClient.create();

describe('Analytics Plugin Integration', () => {
	describe('.registerPlugin', () => {
		it('should be exposed as an Analytics static method', () => {
			Analytics.registerPlugin.should.be.a('function');
		});

		it('should process the given plugin and execute its initialisation logic', function() {
			Analytics.create();

			const plugin = analytics => {
				analytics.should.be.equal(Analytics);
			};

			const spy = sinon.spy(plugin);

			Analytics.registerPlugin(spy);
			assert.isTrue(spy.calledOnce);
		});
	});
});