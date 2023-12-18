/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.aggregation;

import com.liferay.portal.kernel.util.ArrayUtil;
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
import com.liferay.portal.search.opensearch2.internal.util.SetterUtil;
import com.liferay.portal.search.script.Script;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortFieldTranslator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang.ArrayUtils;

import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.aggregations.Aggregation;
import org.opensearch.client.opensearch._types.aggregations.AggregationBuilders;
import org.opensearch.client.opensearch._types.aggregations.AverageBucketAggregation;
import org.opensearch.client.opensearch._types.aggregations.BucketScriptAggregation;
import org.opensearch.client.opensearch._types.aggregations.BucketSelectorAggregation;
import org.opensearch.client.opensearch._types.aggregations.BucketSortAggregation;
import org.opensearch.client.opensearch._types.aggregations.BucketsPath;
import org.opensearch.client.opensearch._types.aggregations.CumulativeSumAggregation;
import org.opensearch.client.opensearch._types.aggregations.DerivativeAggregation;
import org.opensearch.client.opensearch._types.aggregations.ExtendedStatsBucketAggregation;
import org.opensearch.client.opensearch._types.aggregations.MaxBucketAggregation;
import org.opensearch.client.opensearch._types.aggregations.MinBucketAggregation;
import org.opensearch.client.opensearch._types.aggregations.MovingFunctionAggregation;
import org.opensearch.client.opensearch._types.aggregations.PercentilesBucketAggregation;
import org.opensearch.client.opensearch._types.aggregations.SerialDifferencingAggregation;
import org.opensearch.client.opensearch._types.aggregations.StatsBucketAggregation;
import org.opensearch.client.opensearch._types.aggregations.SumBucketAggregation;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Petteri Karttunen
 */
@Component(
	property = "search.engine.impl=OpenSearch",
	service = PipelineAggregationTranslator.class
)
public class OpenSearchPipelineAggregationTranslator
	implements PipelineAggregationTranslator<Aggregation>,
			   PipelineAggregationVisitor<Aggregation> {

	@Override
	public Aggregation translate(PipelineAggregation pipelineAggregation) {
		return pipelineAggregation.accept(this);
	}

	@Override
	public Aggregation visit(
		AvgBucketPipelineAggregation avgBucketPipelineAggregation) {

		AverageBucketAggregation.Builder averageBucketAggregationBuilder =
			AggregationBuilders.avgBucket();

		_setNotBlankBucketsPath(
			averageBucketAggregationBuilder::bucketsPath,
			avgBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			averageBucketAggregationBuilder::format,
			avgBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			averageBucketAggregationBuilder::gapPolicy,
			avgBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(averageBucketAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		BucketScriptPipelineAggregation bucketScriptPipelineAggregation) {

		BucketScriptAggregation.Builder bucketScriptAggregationBuilder =
			AggregationBuilders.bucketScript();

		SetterUtil.setNotBlankString(
			bucketScriptAggregationBuilder::format,
			bucketScriptPipelineAggregation.getFormat());
		_setNotEmptyBucketsPathMap(
			bucketScriptAggregationBuilder::bucketsPath,
			bucketScriptPipelineAggregation.getBucketsPathsMap());
		SetterUtil.setNotNullScript(
			bucketScriptAggregationBuilder::script,
			bucketScriptPipelineAggregation.getScript());

		return new Aggregation(bucketScriptAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation) {

		BucketSelectorAggregation.Builder bucketSelectorAggregationBuilder =
			AggregationBuilders.bucketSelector();

		_setNotEmptyBucketsPathMap(
			bucketSelectorAggregationBuilder::bucketsPath,
			bucketSelectorPipelineAggregation.getBucketsPathsMap());
		_setNotNullGapPolicy(
			bucketSelectorAggregationBuilder::gapPolicy,
			bucketSelectorPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullScript(
			bucketSelectorAggregationBuilder::script,
			bucketSelectorPipelineAggregation.getScript());

		return new Aggregation(bucketSelectorAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		BucketSortPipelineAggregation bucketSortPipelineAggregation) {

		BucketSortAggregation.Builder bucketSortAggregationBuilder =
			AggregationBuilders.bucketSort();

		SetterUtil.setNotNullInteger(
			bucketSortAggregationBuilder::from,
			bucketSortPipelineAggregation.getFrom());
		_setNotNullGapPolicy(
			bucketSortAggregationBuilder::gapPolicy,
			bucketSortPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullInteger(
			bucketSortAggregationBuilder::size,
			bucketSortPipelineAggregation.getSize());

		List<FieldSort> fieldSorts =
			bucketSortPipelineAggregation.getFieldSorts();

		fieldSorts.forEach(
			fieldSort -> bucketSortAggregationBuilder.sort(
				_sortFieldTranslator.translate(fieldSort)));

		return new Aggregation(bucketSortAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		CumulativeSumPipelineAggregation cumulativeSumPipelineAggregation) {

		CumulativeSumAggregation.Builder cumulativeSumAggregationBuilder =
			AggregationBuilders.cumulativeSum();

		_setNotBlankBucketsPath(
			cumulativeSumAggregationBuilder::bucketsPath,
			cumulativeSumPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			cumulativeSumAggregationBuilder::format,
			cumulativeSumPipelineAggregation.getFormat());

		return new Aggregation(cumulativeSumAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		DerivativePipelineAggregation derivativePipelineAggregation) {

		DerivativeAggregation.Builder derivativeAggregationBuilder =
			AggregationBuilders.derivative();

		_setNotBlankBucketsPath(
			derivativeAggregationBuilder::bucketsPath,
			derivativePipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			derivativeAggregationBuilder::format,
			derivativePipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			derivativeAggregationBuilder::gapPolicy,
			derivativePipelineAggregation.getGapPolicy());

		return new Aggregation(derivativeAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		ExtendedStatsBucketPipelineAggregation
			extendedStatsBucketPipelineAggregation) {

		ExtendedStatsBucketAggregation.Builder
			extendedStatsBucketAggregationBuilder =
				AggregationBuilders.extendedStatsBucket();

		_setNotBlankBucketsPath(
			extendedStatsBucketAggregationBuilder::bucketsPath,
			extendedStatsBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			extendedStatsBucketAggregationBuilder::format,
			extendedStatsBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			extendedStatsBucketAggregationBuilder::gapPolicy,
			extendedStatsBucketPipelineAggregation.getGapPolicy());

		if (extendedStatsBucketPipelineAggregation.getSigma() != null) {
			extendedStatsBucketAggregationBuilder.sigma(
				extendedStatsBucketPipelineAggregation.getSigma());
		}

		return new Aggregation(extendedStatsBucketAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		MaxBucketPipelineAggregation maxBucketPipelineAggregation) {

		MaxBucketAggregation.Builder maxBucketAggregationBuilder =
			AggregationBuilders.maxBucket();

		_setNotBlankBucketsPath(
			maxBucketAggregationBuilder::bucketsPath,
			maxBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			maxBucketAggregationBuilder::format,
			maxBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			maxBucketAggregationBuilder::gapPolicy,
			maxBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(maxBucketAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		MinBucketPipelineAggregation minBucketPipelineAggregation) {

		MinBucketAggregation.Builder minBucketAggregationBuilder =
			AggregationBuilders.minBucket();

		_setNotBlankBucketsPath(
			minBucketAggregationBuilder::bucketsPath,
			minBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			minBucketAggregationBuilder::format,
			minBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			minBucketAggregationBuilder::gapPolicy,
			minBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(minBucketAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		MovingFunctionPipelineAggregation movingFunctionPipelineAggregation) {

		MovingFunctionAggregation.Builder movingFunctionAggregationBuilder =
			AggregationBuilders.movingFn();

		Script script = movingFunctionPipelineAggregation.getScript();

		movingFunctionAggregationBuilder.script(script.getIdOrCode());

		_setNotBlankBucketsPath(
			movingFunctionAggregationBuilder::bucketsPath,
			movingFunctionPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			movingFunctionAggregationBuilder::format,
			movingFunctionPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			movingFunctionAggregationBuilder::gapPolicy,
			movingFunctionPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullInteger(
			movingFunctionAggregationBuilder::window,
			movingFunctionPipelineAggregation.getWindow());

		return new Aggregation(movingFunctionAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation) {

		PercentilesBucketAggregation.Builder
			percentilesBucketAggregationBuilder =
				AggregationBuilders.percentilesBucket();

		_setNotBlankBucketsPath(
			percentilesBucketAggregationBuilder::bucketsPath,
			percentilesBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			percentilesBucketAggregationBuilder::format,
			percentilesBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			percentilesBucketAggregationBuilder::gapPolicy,
			percentilesBucketPipelineAggregation.getGapPolicy());

		if (!ArrayUtil.isEmpty(
				percentilesBucketPipelineAggregation.getPercents())) {

			percentilesBucketAggregationBuilder.percents(
				Arrays.asList(
					ArrayUtils.toObject(
						percentilesBucketPipelineAggregation.getPercents())));
		}

		return new Aggregation(percentilesBucketAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		SerialDiffPipelineAggregation serialDiffPipelineAggregation) {

		SerialDifferencingAggregation.Builder
			serialDifferencingAggregationBuilder =
				AggregationBuilders.serialDiff();

		_setNotBlankBucketsPath(
			serialDifferencingAggregationBuilder::bucketsPath,
			serialDiffPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			serialDifferencingAggregationBuilder::format,
			serialDiffPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			serialDifferencingAggregationBuilder::gapPolicy,
			serialDiffPipelineAggregation.getGapPolicy());
		SetterUtil.setNotNullInteger(
			serialDifferencingAggregationBuilder::lag,
			serialDiffPipelineAggregation.getLag());

		return new Aggregation(serialDifferencingAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		StatsBucketPipelineAggregation statsBucketPipelineAggregation) {

		StatsBucketAggregation.Builder statsBucketAggregationBuilder =
			AggregationBuilders.statsBucket();

		_setNotBlankBucketsPath(
			statsBucketAggregationBuilder::bucketsPath,
			statsBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			statsBucketAggregationBuilder::format,
			statsBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			statsBucketAggregationBuilder::gapPolicy,
			statsBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(statsBucketAggregationBuilder.build());
	}

	@Override
	public Aggregation visit(
		SumBucketPipelineAggregation sumBucketPipelineAggregation) {

		SumBucketAggregation.Builder sumBucketAggregationBuilder =
			AggregationBuilders.sumBucket();

		_setNotBlankBucketsPath(
			sumBucketAggregationBuilder::bucketsPath,
			sumBucketPipelineAggregation.getBucketsPath());
		SetterUtil.setNotBlankString(
			sumBucketAggregationBuilder::format,
			sumBucketPipelineAggregation.getFormat());
		_setNotNullGapPolicy(
			sumBucketAggregationBuilder::gapPolicy,
			sumBucketPipelineAggregation.getGapPolicy());

		return new Aggregation(sumBucketAggregationBuilder.build());
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
		Consumer<org.opensearch.client.opensearch._types.aggregations.GapPolicy>
			consumer,
		GapPolicy gapPolicy) {

		if (gapPolicy != null) {
			consumer.accept(_translateGapPolicy(gapPolicy));
		}
	}

	private org.opensearch.client.opensearch._types.aggregations.GapPolicy
		_translateGapPolicy(GapPolicy gapPolicy) {

		if (gapPolicy == GapPolicy.INSTANT_ZEROS) {
			return org.opensearch.client.opensearch._types.aggregations.
				GapPolicy.InsertZeros;
		}
		else if (gapPolicy == GapPolicy.SKIP) {
			return org.opensearch.client.opensearch._types.aggregations.
				GapPolicy.Skip;
		}

		throw new IllegalArgumentException("Invalid gap policy " + gapPolicy);
	}

	@Reference(target = "(search.engine.impl=OpenSearch)")
	private SortFieldTranslator<SortOptions> _sortFieldTranslator;

}