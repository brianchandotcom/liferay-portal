/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.aggregations.AverageBucketAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.BucketScriptAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.BucketSelectorAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.BucketSortAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.BucketsPath;
import co.elastic.clients.elasticsearch._types.aggregations.CumulativeSumAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.DerivativeAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.ExtendedStatsBucketAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.MaxBucketAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.MinBucketAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.MovingFunctionAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.PercentilesBucketAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.SerialDifferencingAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.StatsBucketAggregation;
import co.elastic.clients.elasticsearch._types.aggregations.SumBucketAggregation;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketScriptPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.CumulativeSumPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.DerivativePipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.ExtendedStatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.GapPolicy;
import com.liferay.portal.search.aggregation.pipeline.MaxBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MinBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.MovingFunctionPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationTranslator;
import com.liferay.portal.search.aggregation.pipeline.PipelineAggregationVisitor;
import com.liferay.portal.search.aggregation.pipeline.SerialDiffPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.StatsBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.SumBucketPipelineAggregation;
import com.liferay.portal.search.elasticsearch8.internal.sort.ElasticsearchSortFieldTranslator;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldTranslator;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = PipelineAggregationTranslator.class
)
public class ElasticsearchPipelineAggregationTranslator
	implements PipelineAggregationTranslator<Aggregation>,
			   PipelineAggregationVisitor<Aggregation> {

	@Override
	public Aggregation translate(PipelineAggregation pipelineAggregation) {
		return pipelineAggregation.accept(this);
	}

	@Override
	public Aggregation visit(
		AvgBucketPipelineAggregation avgBucketPipelineAggregation) {

		AverageBucketAggregation.Builder builder =
			AggregationBuilders.avgBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			avgBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, avgBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, avgBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		BucketScriptPipelineAggregation bucketScriptPipelineAggregation) {

		BucketScriptAggregation.Builder builder =
			AggregationBuilders.bucketScript();

		_setNotEmptyBucketsPathMap(
			builder::bucketsPath,
			bucketScriptPipelineAggregation.getBucketsPathsMap());
		SetterUtil.setNotBlankString(
			builder::format, bucketScriptPipelineAggregation.getFormat());
		SetterUtil.setNotNullScript(
			builder::script, bucketScriptPipelineAggregation.getScript());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation) {

		BucketSelectorAggregation.Builder builder =
			AggregationBuilders.bucketSelector();

		_setNotEmptyBucketsPathMap(
			builder::bucketsPath,
			bucketSelectorPipelineAggregation.getBucketsPathsMap());
		_setNotNullGapPolicy(
			builder::gapPolicy,
			bucketSelectorPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullScript(
			builder::script, bucketSelectorPipelineAggregation.getScript());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		BucketSortPipelineAggregation bucketSortPipelineAggregation) {

		BucketSortAggregation.Builder builder =
			AggregationBuilders.bucketSort();

		SetterUtil.setNotNullInteger(
			builder::from, bucketSortPipelineAggregation.getFrom());
		_setNotNullGapPolicy(
			builder::gapPolicy, bucketSortPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullInteger(
			builder::size, bucketSortPipelineAggregation.getSize());

		List<FieldSort> fieldSorts =
			bucketSortPipelineAggregation.getFieldSorts();

		fieldSorts.forEach(
			fieldSort -> builder.sort(
				_sortFieldTranslator.translate(fieldSort)));

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		CumulativeSumPipelineAggregation cumulativeSumPipelineAggregation) {

		CumulativeSumAggregation.Builder builder =
			AggregationBuilders.cumulativeSum();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			cumulativeSumPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, cumulativeSumPipelineAggregation.getFormat());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		DerivativePipelineAggregation derivativePipelineAggregation) {

		DerivativeAggregation.Builder builder =
			AggregationBuilders.derivative();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			derivativePipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, derivativePipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, derivativePipelineAggregation.getGapPolicy());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		ExtendedStatsBucketPipelineAggregation
			extendedStatsBucketPipelineAggregation) {

		ExtendedStatsBucketAggregation.Builder builder =
			AggregationBuilders.extendedStatsBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			extendedStatsBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format,
			extendedStatsBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy,
			extendedStatsBucketPipelineAggregation.getGapPolicy());

		if (extendedStatsBucketPipelineAggregation.getSigma() != null) {
			builder.sigma(extendedStatsBucketPipelineAggregation.getSigma());
		}

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		MaxBucketPipelineAggregation maxBucketPipelineAggregation) {

		MaxBucketAggregation.Builder builder = AggregationBuilders.maxBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			maxBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, maxBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, maxBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		MinBucketPipelineAggregation minBucketPipelineAggregation) {

		MinBucketAggregation.Builder builder = AggregationBuilders.minBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			minBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, minBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, minBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation) {

		MovingFunctionAggregation.Builder builder =
			AggregationBuilders.movingFn();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			movingFunctionPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, movingFunctionPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy,
			movingFunctionPipelineAggregation.getGapPolicy());

		Script script = movingFunctionPipelineAggregation.getScript();

		builder.script(script.getIdOrCode());

		SetterUtil.setNotNullInteger(
			builder::window, movingFunctionPipelineAggregation.getWindow());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation) {

		PercentilesBucketAggregation.Builder builder =
			AggregationBuilders.percentilesBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			percentilesBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, percentilesBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy,
			percentilesBucketPipelineAggregation.getGapPolicy());

		if (ArrayUtil.isNotEmpty(
				percentilesBucketPipelineAggregation.getPercents())) {

			builder.percents(
				ListUtil.fromArray(
					percentilesBucketPipelineAggregation.getPercents()));
		}

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		SerialDiffPipelineAggregation serialDiffPipelineAggregation) {

		SerialDifferencingAggregation.Builder builder =
			AggregationBuilders.serialDiff();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			serialDiffPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, serialDiffPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, serialDiffPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullInteger(
			builder::lag, serialDiffPipelineAggregation.getLag());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		StatsBucketPipelineAggregation statsBucketPipelineAggregation) {

		StatsBucketAggregation.Builder builder =
			AggregationBuilders.statsBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			statsBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, statsBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, statsBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(builder.build());
	}

	@Override
	public Aggregation visit(
		SumBucketPipelineAggregation sumBucketPipelineAggregation) {

		SumBucketAggregation.Builder builder = AggregationBuilders.sumBucket();

		_setNotBlankBucketsPath(
			builder::bucketsPath,
			sumBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			builder::format, sumBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			builder::gapPolicy, sumBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(builder.build());
	}

	private void _setNotBlankBucketsPath(
		Consumer<BucketsPath> consumer, String value) {

		if (!Validator.isBlank(value)) {
			consumer.accept(
				BucketsPath.of(bucketsPath -> bucketsPath.single(value)));
		}
	}

	private void _setNotEmptyBucketsPathMap(
		Consumer<BucketsPath> consumer, Map<String, String> values) {

		if (MapUtil.isNotEmpty(values)) {
			consumer.accept(
				BucketsPath.of(bucketsPath -> bucketsPath.dict(values)));
		}
	}

	private void _setNotNullGapPolicy(
		Consumer<co.elastic.clients.elasticsearch._types.aggregations.GapPolicy>
			consumer,
		GapPolicy gapPolicy) {

		if (gapPolicy != null) {
			consumer.accept(_translateGapPolicy(gapPolicy));
		}
	}

	private co.elastic.clients.elasticsearch._types.aggregations.GapPolicy
		_translateGapPolicy(GapPolicy gapPolicy) {

		if (gapPolicy == GapPolicy.INSTANT_ZEROS) {
			return co.elastic.clients.elasticsearch._types.aggregations.
				GapPolicy.InsertZeros;
		}
		else if (gapPolicy == GapPolicy.SKIP) {
			return co.elastic.clients.elasticsearch._types.aggregations.
				GapPolicy.Skip;
		}

		throw new IllegalArgumentException("Invalid gap policy " + gapPolicy);
	}

	private final SortFieldTranslator<SortOptions> _sortFieldTranslator =
		new ElasticsearchSortFieldTranslator();

}