/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.aggregation;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.aggregation.pipeline.AvgBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketMetricsPipelineAggregation;
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
import com.liferay.portal.search.elasticsearch8.internal.script.ScriptTranslator;
import com.liferay.portal.search.elasticsearch8.internal.sort.ElasticsearchSortFieldTranslator;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldTranslator;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.search.aggregations.PipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.PipelineAggregatorBuilders;
import org.elasticsearch.search.aggregations.pipeline.AvgBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketHelpers;
import org.elasticsearch.search.aggregations.pipeline.BucketMetricsPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketScriptPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketSelectorPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.BucketSortPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.CumulativeSumPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.DerivativePipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.ExtendedStatsBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.MaxBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.MinBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.MovFnPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.PercentilesBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.SerialDiffPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.StatsBucketPipelineAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.SumBucketPipelineAggregationBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	property = "search.engine.impl=Elasticsearch",
	service = PipelineAggregationTranslator.class
)
public class ElasticsearchPipelineAggregationTranslator
	implements PipelineAggregationTranslator<PipelineAggregationBuilder>,
			   PipelineAggregationVisitor<PipelineAggregationBuilder> {

	@Override
	public PipelineAggregationBuilder translate(
		PipelineAggregation pipelineAggregation) {

		return pipelineAggregation.accept(this);
	}

	@Override
	public PipelineAggregationBuilder visit(
		AvgBucketPipelineAggregation avgBucketPipelineAggregation) {

		AvgBucketPipelineAggregationBuilder
			avgBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.avgBucket(
					avgBucketPipelineAggregation.getName(),
					avgBucketPipelineAggregation.getBucketsPath());

		_assemble(
			avgBucketPipelineAggregation, avgBucketPipelineAggregationBuilder);

		return avgBucketPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		BucketScriptPipelineAggregation bucketScriptPipelineAggregation) {

		BucketScriptPipelineAggregationBuilder
			bucketScriptPipelineAggregationBuilder =
				PipelineAggregatorBuilders.bucketScript(
					bucketScriptPipelineAggregation.getName(),
					bucketScriptPipelineAggregation.getBucketsPathsMap(),
					_scriptTranslator.translate(
						bucketScriptPipelineAggregation.getScript()));

		if (bucketScriptPipelineAggregation.getFormat() != null) {
			bucketScriptPipelineAggregationBuilder.format(
				bucketScriptPipelineAggregation.getFormat());
		}

		return bucketScriptPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation) {

		BucketSelectorPipelineAggregationBuilder
			bucketScriptPipelineAggregationBuilder =
				PipelineAggregatorBuilders.bucketSelector(
					bucketSelectorPipelineAggregation.getName(),
					bucketSelectorPipelineAggregation.getBucketsPathsMap(),
					_scriptTranslator.translate(
						bucketSelectorPipelineAggregation.getScript()));

		if (bucketSelectorPipelineAggregation.getGapPolicy() != null) {
			bucketScriptPipelineAggregationBuilder.gapPolicy(
				_translate(bucketSelectorPipelineAggregation.getGapPolicy()));
		}

		return bucketScriptPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		BucketSortPipelineAggregation bucketSortPipelineAggregation) {

		List<FieldSort> fieldSorts =
			bucketSortPipelineAggregation.getFieldSorts();

		List<FieldSortBuilder> fieldSortBuilders = new ArrayList<>(
			fieldSorts.size());

		fieldSorts.forEach(
			fieldSort -> {
				FieldSortBuilder fieldSortBuilder =
					(FieldSortBuilder)_sortFieldTranslator.translate(fieldSort);

				fieldSortBuilders.add(fieldSortBuilder);
			});

		BucketSortPipelineAggregationBuilder
			bucketSortPipelineAggregationBuilder =
				PipelineAggregatorBuilders.bucketSort(
					bucketSortPipelineAggregation.getName(), fieldSortBuilders);

		if (bucketSortPipelineAggregation.getGapPolicy() != null) {
			bucketSortPipelineAggregationBuilder.gapPolicy(
				_translate(bucketSortPipelineAggregation.getGapPolicy()));
		}

		if (bucketSortPipelineAggregation.getFrom() != null) {
			bucketSortPipelineAggregationBuilder.from(
				bucketSortPipelineAggregation.getFrom());
		}

		if (bucketSortPipelineAggregation.getSize() != null) {
			bucketSortPipelineAggregationBuilder.size(
				bucketSortPipelineAggregation.getSize());
		}

		return bucketSortPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		CumulativeSumPipelineAggregation cumulativeSumPipelineAggregation) {

		CumulativeSumPipelineAggregationBuilder
			cumulativeSumPipelineAggregationBuilder =
				PipelineAggregatorBuilders.cumulativeSum(
					cumulativeSumPipelineAggregation.getName(),
					cumulativeSumPipelineAggregation.getBucketsPath());

		if (cumulativeSumPipelineAggregation.getFormat() != null) {
			cumulativeSumPipelineAggregationBuilder.format(
				cumulativeSumPipelineAggregation.getFormat());
		}

		return cumulativeSumPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		DerivativePipelineAggregation derivativePipelineAggregation) {

		DerivativePipelineAggregationBuilder
			derivativePipelineAggregationBuilder =
				PipelineAggregatorBuilders.derivative(
					derivativePipelineAggregation.getName(),
					derivativePipelineAggregation.getBucketsPath());

		if (derivativePipelineAggregation.getFormat() != null) {
			derivativePipelineAggregationBuilder.format(
				derivativePipelineAggregation.getFormat());
		}

		if (derivativePipelineAggregation.getGapPolicy() != null) {
			derivativePipelineAggregationBuilder.gapPolicy(
				_translate(derivativePipelineAggregation.getGapPolicy()));
		}

		if (derivativePipelineAggregation.getUnit() != null) {
			derivativePipelineAggregationBuilder.unit(
				derivativePipelineAggregation.getUnit());
		}

		return derivativePipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		ExtendedStatsBucketPipelineAggregation
			extendedStatsBucketPipelineAggregation) {

		ExtendedStatsBucketPipelineAggregationBuilder
			extendedStatsBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.extendedStatsBucket(
					extendedStatsBucketPipelineAggregation.getName(),
					extendedStatsBucketPipelineAggregation.getBucketsPath());

		_assemble(
			extendedStatsBucketPipelineAggregation,
			extendedStatsBucketPipelineAggregationBuilder);

		if (extendedStatsBucketPipelineAggregation.getSigma() != null) {
			extendedStatsBucketPipelineAggregationBuilder.sigma(
				extendedStatsBucketPipelineAggregation.getSigma());
		}

		return extendedStatsBucketPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		MaxBucketPipelineAggregation maxBucketPipelineAggregation) {

		MaxBucketPipelineAggregationBuilder
			maxBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.maxBucket(
					maxBucketPipelineAggregation.getName(),
					maxBucketPipelineAggregation.getBucketsPath());

		_assemble(
			maxBucketPipelineAggregation, maxBucketPipelineAggregationBuilder);

		return maxBucketPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		MinBucketPipelineAggregation minBucketPipelineAggregation) {

		MinBucketPipelineAggregationBuilder
			minBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.minBucket(
					minBucketPipelineAggregation.getName(),
					minBucketPipelineAggregation.getBucketsPath());

		_assemble(
			minBucketPipelineAggregation, minBucketPipelineAggregationBuilder);

		return minBucketPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation) {

		MovFnPipelineAggregationBuilder movFnPipelineAggregationBuilder =
			PipelineAggregatorBuilders.movingFunction(
				movingFunctionPipelineAggregation.getName(),
				_scriptTranslator.translate(
					movingFunctionPipelineAggregation.getScript()),
				movingFunctionPipelineAggregation.getBucketsPath(),
				movingFunctionPipelineAggregation.getWindow());

		if (movingFunctionPipelineAggregation.getFormat() != null) {
			movFnPipelineAggregationBuilder.format(
				movingFunctionPipelineAggregation.getFormat());
		}

		if (movingFunctionPipelineAggregation.getGapPolicy() != null) {
			movFnPipelineAggregationBuilder.gapPolicy(
				_translate(movingFunctionPipelineAggregation.getGapPolicy()));
		}

		return movFnPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation) {

		PercentilesBucketPipelineAggregationBuilder
			percentilesBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.percentilesBucket(
					percentilesBucketPipelineAggregation.getName(),
					percentilesBucketPipelineAggregation.getBucketsPath());

		_assemble(
			percentilesBucketPipelineAggregation,
			percentilesBucketPipelineAggregationBuilder);

		if (ArrayUtil.isNotEmpty(
				percentilesBucketPipelineAggregation.getPercents())) {

			percentilesBucketPipelineAggregationBuilder.setPercents(
				percentilesBucketPipelineAggregation.getPercents());
		}

		return percentilesBucketPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		SerialDiffPipelineAggregation serialDiffPipelineAggregation) {

		SerialDiffPipelineAggregationBuilder
			serialDiffPipelineAggregationBuilder =
				PipelineAggregatorBuilders.diff(
					serialDiffPipelineAggregation.getName(),
					serialDiffPipelineAggregation.getBucketsPath());

		if (serialDiffPipelineAggregation.getFormat() != null) {
			serialDiffPipelineAggregationBuilder.format(
				serialDiffPipelineAggregation.getFormat());
		}

		if (serialDiffPipelineAggregation.getGapPolicy() != null) {
			serialDiffPipelineAggregationBuilder.gapPolicy(
				_translate(serialDiffPipelineAggregation.getGapPolicy()));
		}

		if (serialDiffPipelineAggregation.getLag() != null) {
			serialDiffPipelineAggregationBuilder.lag(
				serialDiffPipelineAggregation.getLag());
		}

		return serialDiffPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		StatsBucketPipelineAggregation statsBucketPipelineAggregation) {

		StatsBucketPipelineAggregationBuilder
			statsBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.statsBucket(
					statsBucketPipelineAggregation.getName(),
					statsBucketPipelineAggregation.getBucketsPath());

		_assemble(
			statsBucketPipelineAggregation,
			statsBucketPipelineAggregationBuilder);

		return statsBucketPipelineAggregationBuilder;
	}

	@Override
	public PipelineAggregationBuilder visit(
		SumBucketPipelineAggregation sumBucketPipelineAggregation) {

		SumBucketPipelineAggregationBuilder
			sumBucketPipelineAggregationBuilder =
				PipelineAggregatorBuilders.sumBucket(
					sumBucketPipelineAggregation.getName(),
					sumBucketPipelineAggregation.getBucketsPath());

		_assemble(
			sumBucketPipelineAggregation, sumBucketPipelineAggregationBuilder);

		return sumBucketPipelineAggregationBuilder;
	}

	private void _assemble(
		BucketMetricsPipelineAggregation bucketMetricsPipelineAggregation,
		BucketMetricsPipelineAggregationBuilder
			bucketMetricsPipelineAggregationBuilder) {

		if (bucketMetricsPipelineAggregation.getFormat() != null) {
			bucketMetricsPipelineAggregationBuilder.format(
				bucketMetricsPipelineAggregation.getFormat());
		}

		if (bucketMetricsPipelineAggregation.getGapPolicy() != null) {
			bucketMetricsPipelineAggregationBuilder.gapPolicy(
				_translate(bucketMetricsPipelineAggregation.getGapPolicy()));
		}
	}

	private BucketHelpers.GapPolicy _translate(GapPolicy gapPolicy) {
		if (gapPolicy == GapPolicy.INSTANT_ZEROS) {
			return BucketHelpers.GapPolicy.INSERT_ZEROS;
		}
		else if (gapPolicy == GapPolicy.SKIP) {
			return BucketHelpers.GapPolicy.SKIP;
		}

		throw new IllegalArgumentException("Invalid gap policy" + gapPolicy);
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();
	private final SortFieldTranslator<SortBuilder<?>> _sortFieldTranslator =
		new ElasticsearchSortFieldTranslator();

}