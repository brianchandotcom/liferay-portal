package com.liferay.parser.apio.internal.architect.filter;

import com.liferay.parser.apio.architect.entity.EntityField;
import com.liferay.parser.apio.architect.entity.EntityModel;
import com.liferay.parser.apio.architect.filter.expression.BinaryExpression;
import com.liferay.parser.apio.architect.filter.expression.ExpressionVisitException;
import com.liferay.parser.apio.architect.filter.expression.LiteralExpression;
import com.liferay.parser.apio.internal.architect.filter.expression.BinaryExpressionImpl;
import com.liferay.parser.apio.internal.architect.filter.expression.LiteralExpressionImpl;
import com.liferay.parser.apio.internal.architect.filter.expression.MemberExpressionImpl;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.FastDateFormatFactory;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class ExpressionConvertImplTest {

	@Before
	public void setUp() {
		PropsUtil.setProps(Mockito.mock(Props.class));

		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			Mockito.mock(FastDateFormatFactory.class));
	}

	@Test
	public void testConvert() throws ExpressionVisitException {

		BinaryExpression binaryExpression =
			new BinaryExpressionImpl(
				new MemberExpressionImpl(Collections.singletonList("title")),
				BinaryExpression.Operation.EQ,
				new LiteralExpressionImpl("test", LiteralExpression.Type.STRING));

		TermFilter termFilter =
			(TermFilter)_expressionConvert.convert(
			binaryExpression, Locale.getDefault(), _entityModel);

		Assert.assertEquals("title", termFilter.getField());
		Assert.assertEquals("test", termFilter.getValue());

	}

	private static final EntityModel _entityModel = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Stream.of(
				new EntityField(
					"title", EntityField.Type.STRING, locale -> "title")
			).collect(
				Collectors.toMap(EntityField::getName, Function.identity())
			);
		}

		@Override
		public String getName() {
			return "SomeEntityName";
		}

	};

	private ExpressionConvertImpl _expressionConvert =
		new ExpressionConvertImpl();

}
