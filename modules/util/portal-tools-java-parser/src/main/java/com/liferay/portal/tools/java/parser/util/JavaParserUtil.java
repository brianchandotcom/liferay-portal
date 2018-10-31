/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.tools.java.parser.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.tools.java.parser.JavaAnnotation;
import com.liferay.portal.tools.java.parser.JavaAnnotationMemberValuePair;
import com.liferay.portal.tools.java.parser.JavaArray;
import com.liferay.portal.tools.java.parser.JavaArrayDeclarator;
import com.liferay.portal.tools.java.parser.JavaArrayElement;
import com.liferay.portal.tools.java.parser.JavaClassCall;
import com.liferay.portal.tools.java.parser.JavaConstructor;
import com.liferay.portal.tools.java.parser.JavaExpression;
import com.liferay.portal.tools.java.parser.JavaIfStatement;
import com.liferay.portal.tools.java.parser.JavaInstanceofStatement;
import com.liferay.portal.tools.java.parser.JavaLambdaExpression;
import com.liferay.portal.tools.java.parser.JavaLambdaParameter;
import com.liferay.portal.tools.java.parser.JavaMethod;
import com.liferay.portal.tools.java.parser.JavaMethodCall;
import com.liferay.portal.tools.java.parser.JavaMethodReference;
import com.liferay.portal.tools.java.parser.JavaNewArrayInstantiation;
import com.liferay.portal.tools.java.parser.JavaNewClassInstantiation;
import com.liferay.portal.tools.java.parser.JavaOperator;
import com.liferay.portal.tools.java.parser.JavaOperatorExpression;
import com.liferay.portal.tools.java.parser.JavaParameter;
import com.liferay.portal.tools.java.parser.JavaSignature;
import com.liferay.portal.tools.java.parser.JavaSimpleLambdaExpression;
import com.liferay.portal.tools.java.parser.JavaSimpleValue;
import com.liferay.portal.tools.java.parser.JavaTernaryOperator;
import com.liferay.portal.tools.java.parser.JavaType;
import com.liferay.portal.tools.java.parser.JavaTypeCast;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaParserUtil {

	public static JavaConstructor parseJavaConstructor(
		DetailAST constructorDefAST) {

		JavaConstructor javaConstructor = new JavaConstructor();

		javaConstructor.setJavaAnnotations(
			_parseJavaAnnotations(
				constructorDefAST.findFirstToken(TokenTypes.MODIFIERS)));
		javaConstructor.setJavaSignature(
			_parseJavaSignature(constructorDefAST));

		return javaConstructor;
	}

	public static JavaExpression parseJavaExpression(DetailAST detailAST) {
		if (detailAST.getType() == TokenTypes.EXPR) {
			detailAST = detailAST.getFirstChild();
		}

		boolean hasSurroundingParentheses = false;

		if (detailAST.getType() == TokenTypes.LPAREN) {
			detailAST = detailAST.getNextSibling();

			hasSurroundingParentheses = true;
		}
		else if (detailAST.getType() == TokenTypes.RPAREN) {
			detailAST = detailAST.getPreviousSibling();

			hasSurroundingParentheses = true;
		}

		JavaExpression javaExpression = null;

		if (detailAST.getType() == TokenTypes.ANNOTATION) {
			javaExpression = _parseJavaAnnotation(detailAST);
		}
		else if ((detailAST.getType() == TokenTypes.ANNOTATION_ARRAY_INIT) ||
				 (detailAST.getType() == TokenTypes.ARRAY_INIT)) {

			javaExpression = _parseJavaArray(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			javaExpression = _parseJavaArrayDeclarator(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.DOT) {
			javaExpression = parseJavaExpression(detailAST.getFirstChild());

			javaExpression.setChainedJavaExpression(
				parseJavaExpression(detailAST.getLastChild()));
		}
		else if (detailAST.getType() == TokenTypes.INDEX_OP) {
			javaExpression = _parseJavaArrayElement(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.LAMBDA) {
			javaExpression = _parseJavaLambdaExpression(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_INSTANCEOF) {
			javaExpression = _parseJavaInstanceofStatement(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.LITERAL_NEW) {
			DetailAST arrayDeclaratorAST = detailAST.findFirstToken(
				TokenTypes.ARRAY_DECLARATOR);

			if (arrayDeclaratorAST != null) {
				javaExpression = _parseJavaNewArrayInstantiation(detailAST);
			}
			else {
				DetailAST elistAST = detailAST.findFirstToken(TokenTypes.ELIST);

				if (elistAST != null) {
					javaExpression = _parseJavaNewClassInstantiation(detailAST);
				}
			}
		}
		else if (detailAST.getType() == TokenTypes.METHOD_CALL) {
			javaExpression = _parseJavaMethodCall(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.METHOD_REF) {
			javaExpression = _parseJavaMethodReference(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.QUESTION) {
			javaExpression = _parseJavaTernaryOperator(detailAST);
		}
		else if (detailAST.getType() == TokenTypes.TYPECAST) {
			javaExpression = _parseJavaTypeCast(detailAST);
		}
		else if (ArrayUtil.contains(_SIMPLE_TYPES, detailAST.getType())) {
			javaExpression = new JavaSimpleValue(detailAST.getText());
		}
		else {
			for (JavaOperator operator : JavaOperator.values()) {
				if (operator.getType() == detailAST.getType()) {
					javaExpression = _parseJavaOperatorExpression(
						detailAST, operator);

					break;
				}
			}
		}

		if ((javaExpression != null) && hasSurroundingParentheses) {
			javaExpression.setHasSurroundingParentheses(true);
		}

		return javaExpression;
	}

	public static JavaIfStatement parseJavaIfStatement(DetailAST literalIfAST) {
		JavaIfStatement javaIfStatement = new JavaIfStatement();

		DetailAST firstChildAST = literalIfAST.getFirstChild();

		javaIfStatement.setConditionJavaExpression(
			parseJavaExpression(firstChildAST.getNextSibling()));

		return javaIfStatement;
	}

	public static JavaMethod parseJavaMethod(DetailAST methodDefAST) {
		JavaMethod javaMethod = new JavaMethod();

		javaMethod.setJavaAnnotations(
			_parseJavaAnnotations(
				methodDefAST.findFirstToken(TokenTypes.MODIFIERS)));
		javaMethod.setJavaSignature(_parseJavaSignature(methodDefAST));

		DetailAST lastChildAST = methodDefAST.getLastChild();

		if (lastChildAST.getType() == TokenTypes.SLIST) {
			javaMethod.setHasBody(true);
		}

		return javaMethod;
	}

	private static String _getName(DetailAST detailAST) {
		DetailAST identAST = detailAST.findFirstToken(TokenTypes.IDENT);

		if (identAST != null) {
			return identAST.getText();
		}

		DetailAST firstChildAST = detailAST.getFirstChild();

		if (ArrayUtil.contains(_SIMPLE_TYPES, firstChildAST.getType())) {
			return firstChildAST.getText();
		}

		DetailAST dotAST = detailAST.findFirstToken(TokenTypes.DOT);

		FullIdent fullIdent = FullIdent.createFullIdent(dotAST);

		return fullIdent.getText();
	}

	private static List<JavaExpression> _parseArrayValueJavaExpressions(
		DetailAST detailAST) {

		int bracketType = detailAST.getType();

		List<JavaExpression> arrayValueJavaExpressions = new ArrayList<>();

		DetailAST firstChildAST = detailAST;

		while (true) {
			if (firstChildAST.getType() != bracketType) {
				if (arrayValueJavaExpressions.size() > 1) {
					Collections.reverse(arrayValueJavaExpressions);
				}

				return arrayValueJavaExpressions;
			}

			DetailAST closeBracketAST = firstChildAST.findFirstToken(
				TokenTypes.RBRACK);

			DetailAST previousSiblingAST = closeBracketAST.getPreviousSibling();

			if ((previousSiblingAST == null) ||
				(previousSiblingAST.getType() == bracketType)) {

				arrayValueJavaExpressions.add(
					new JavaSimpleValue(StringPool.BLANK));
			}
			else {
				arrayValueJavaExpressions.add(
					parseJavaExpression(previousSiblingAST));
			}

			firstChildAST = firstChildAST.getFirstChild();
		}
	}

	private static List<JavaExpression> _parseExceptionJavaExpressions(
		DetailAST throwsAST) {

		List<JavaExpression> exceptionJavaExpressions = new ArrayList<>();

		if (throwsAST == null) {
			return exceptionJavaExpressions;
		}

		DetailAST childAST = throwsAST.getFirstChild();

		while (true) {
			if (childAST == null) {
				return exceptionJavaExpressions;
			}

			if (childAST.getType() != TokenTypes.COMMA) {
				exceptionJavaExpressions.add(parseJavaExpression(childAST));
			}

			childAST = childAST.getNextSibling();
		}
	}

	private static JavaType _parseGenericBoundJavaType(DetailAST detailAST) {
		FullIdent fullIdent = FullIdent.createFullIdent(detailAST);

		JavaType genericBoundJavaType = new JavaType(fullIdent.getText(), 0);

		DetailAST typeArgumentsAST = null;

		if (detailAST.getType() != TokenTypes.DOT) {
			typeArgumentsAST = detailAST.getNextSibling();
		}
		else {
			typeArgumentsAST = detailAST.getLastChild();
		}

		if ((typeArgumentsAST != null) &&
			(typeArgumentsAST.getType() == TokenTypes.TYPE_ARGUMENTS)) {

			genericBoundJavaType.setGenericJavaTypes(
				_parseGenericJavaTypes(
					typeArgumentsAST, TokenTypes.TYPE_ARGUMENT));
		}

		return genericBoundJavaType;
	}

	private static List<JavaType> _parseGenericBoundJavaTypes(
		DetailAST detailAST) {

		List<JavaType> genericBoundJavaTypes = new ArrayList<>();

		DetailAST childAST = detailAST.getFirstChild();

		while (true) {
			if (childAST == null) {
				return genericBoundJavaTypes;
			}

			if ((childAST.getType() != TokenTypes.TYPE_ARGUMENTS) &&
				(childAST.getType() != TokenTypes.TYPE_EXTENSION_AND)) {

				genericBoundJavaTypes.add(_parseGenericBoundJavaType(childAST));
			}

			childAST = childAST.getNextSibling();
		}
	}

	private static List<JavaType> _parseGenericJavaTypes(
		DetailAST groupAST, int type) {

		if (groupAST == null) {
			return null;
		}

		List<JavaType> genericJavaTypes = new ArrayList<>();

		List<DetailAST> detailAstList = DetailASTUtil.getAllChildTokens(
			groupAST, false, type);

		for (DetailAST currentDetailAST : detailAstList) {
			DetailAST childAST = currentDetailAST.getFirstChild();

			if (childAST.getType() == TokenTypes.TYPE) {
				genericJavaTypes.add(_parseJavaType(childAST));
			}
			else {
				genericJavaTypes.add(_parseJavaType(currentDetailAST));
			}
		}

		return genericJavaTypes;
	}

	private static JavaAnnotation _parseJavaAnnotation(
		DetailAST annotationAST) {

		JavaAnnotation javaAnnotation = new JavaAnnotation(
			_getName(annotationAST));

		DetailAST lparenAST = annotationAST.findFirstToken(TokenTypes.LPAREN);

		if (lparenAST == null) {
			return javaAnnotation;
		}

		List<JavaAnnotationMemberValuePair> javaAnnotationMemberValuePairs =
			_parseJavaAnnotationMemberValuePairs(annotationAST);

		if (!javaAnnotationMemberValuePairs.isEmpty()) {
			javaAnnotation.setJavaAnnotationMemberValuePairs(
				javaAnnotationMemberValuePairs);
		}
		else {
			javaAnnotation.setValueJavaExpression(
				parseJavaExpression(lparenAST.getNextSibling()));
		}

		return javaAnnotation;
	}

	private static JavaAnnotationMemberValuePair
		_parseJavaAnnotationMemberValuePair(
			DetailAST annotationMemberValuePairAST) {

		DetailAST identAST = annotationMemberValuePairAST.findFirstToken(
			TokenTypes.IDENT);

		JavaAnnotationMemberValuePair javaAnnotationMemberValuePair =
			new JavaAnnotationMemberValuePair(identAST.getText());

		DetailAST lastChildAST = annotationMemberValuePairAST.getLastChild();

		JavaExpression valueExpression = null;

		if (lastChildAST.getType() == TokenTypes.ANNOTATION_ARRAY_INIT) {
			valueExpression = _parseJavaArray(lastChildAST);
		}
		else if (lastChildAST.getType() == TokenTypes.EXPR) {
			valueExpression = parseJavaExpression(lastChildAST);
		}

		javaAnnotationMemberValuePair.setValueJavaExpression(valueExpression);

		return javaAnnotationMemberValuePair;
	}

	private static List<JavaAnnotationMemberValuePair>
		_parseJavaAnnotationMemberValuePairs(DetailAST annotationAST) {

		List<JavaAnnotationMemberValuePair> javaAnnotationMemberValuePairs =
			new ArrayList<>();

		List<DetailAST> annotationMemberValuePairASTList =
			DetailASTUtil.getAllChildTokens(
				annotationAST, false, TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR);

		for (DetailAST annotationMemberValuePairAST :
				annotationMemberValuePairASTList) {

			javaAnnotationMemberValuePairs.add(
				_parseJavaAnnotationMemberValuePair(
					annotationMemberValuePairAST));
		}

		return javaAnnotationMemberValuePairs;
	}

	private static List<JavaAnnotation> _parseJavaAnnotations(
		DetailAST modifiersAST) {

		List<JavaAnnotation> javaAnnotations = new ArrayList<>();

		List<DetailAST> annotationASTList = DetailASTUtil.getAllChildTokens(
			modifiersAST, false, TokenTypes.ANNOTATION);

		for (DetailAST annotationAST : annotationASTList) {
			javaAnnotations.add(_parseJavaAnnotation(annotationAST));
		}

		return javaAnnotations;
	}

	private static JavaArray _parseJavaArray(DetailAST arrayAST) {
		JavaArray javaArray = new JavaArray();

		DetailAST childAST = arrayAST.getFirstChild();

		while (true) {
			if ((childAST == null) ||
				(childAST.getType() == TokenTypes.RCURLY)) {

				return javaArray;
			}

			javaArray.addValueJavaExpression(parseJavaExpression(childAST));

			childAST = childAST.getNextSibling();
			childAST = childAST.getNextSibling();
		}
	}

	private static JavaArrayDeclarator _parseJavaArrayDeclarator(
		DetailAST arrayDeclaratorAST) {

		List<JavaExpression> dimensionValueJavaExpressions = new ArrayList<>();

		dimensionValueJavaExpressions.add(
			new JavaSimpleValue(StringPool.BLANK));

		DetailAST childAST = arrayDeclaratorAST.getFirstChild();

		while (true) {
			if (childAST.getType() != TokenTypes.ARRAY_DECLARATOR) {
				JavaArrayDeclarator javaArrayDeclarator =
					new JavaArrayDeclarator(childAST.getText());

				javaArrayDeclarator.setDimensionValueJavaExpressions(
					dimensionValueJavaExpressions);

				return javaArrayDeclarator;
			}

			dimensionValueJavaExpressions.add(
				new JavaSimpleValue(StringPool.BLANK));

			childAST = childAST.getFirstChild();
		}
	}

	private static JavaArrayElement _parseJavaArrayElement(
		DetailAST indexOpAST) {

		JavaArrayElement javaArrayElement = null;

		DetailAST firstChildAST = indexOpAST.getFirstChild();

		while (true) {
			if (firstChildAST.getType() != TokenTypes.INDEX_OP) {
				javaArrayElement = new JavaArrayElement(
					parseJavaExpression(firstChildAST));

				break;
			}

			firstChildAST = firstChildAST.getFirstChild();
		}

		javaArrayElement.setIndexValueJavaExpressions(
			_parseArrayValueJavaExpressions(indexOpAST));

		return javaArrayElement;
	}

	private static JavaClassCall _parseJavaClassCall(DetailAST detailAST) {
		JavaClassCall javaClassCall = new JavaClassCall(_getName(detailAST));

		javaClassCall.setParameterValueJavaExpressions(
			_parseParameterValueJavaExpressions(
				detailAST.findFirstToken(TokenTypes.ELIST)));

		DetailAST classInfoAST = detailAST;

		while (true) {
			DetailAST firstChildAST = classInfoAST.getFirstChild();

			if (firstChildAST.getType() != TokenTypes.DOT) {
				break;
			}

			classInfoAST = firstChildAST;
		}

		javaClassCall.setGenericJavaTypes(
			_parseGenericJavaTypes(
				classInfoAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));

		return javaClassCall;
	}

	private static JavaInstanceofStatement _parseJavaInstanceofStatement(
		DetailAST literalInstanceofAST) {

		DetailAST typeAST = literalInstanceofAST.findFirstToken(
			TokenTypes.TYPE);

		JavaInstanceofStatement javaInstanceofStatement =
			new JavaInstanceofStatement(_parseJavaType(typeAST));

		javaInstanceofStatement.setValue(
			parseJavaExpression(literalInstanceofAST.getFirstChild()));

		return javaInstanceofStatement;
	}

	private static JavaExpression _parseJavaLambdaExpression(
		DetailAST lambdaAST) {

		DetailAST firstChildAST = lambdaAST.getFirstChild();

		if (firstChildAST.getType() == TokenTypes.IDENT) {
			return new JavaSimpleLambdaExpression(firstChildAST.getText());
		}

		return new JavaLambdaExpression(
			_parseJavaLambdaParameters(
				lambdaAST.findFirstToken(TokenTypes.PARAMETERS)));
	}

	private static List<JavaLambdaParameter> _parseJavaLambdaParameters(
		DetailAST parametersAST) {

		List<JavaLambdaParameter> javaLambdaParameters = new ArrayList<>();

		List<DetailAST> parameterDefASTList = DetailASTUtil.getAllChildTokens(
			parametersAST, false, TokenTypes.PARAMETER_DEF);

		for (DetailAST parameterDefAST : parameterDefASTList) {
			JavaLambdaParameter javaLambdaParameter = new JavaLambdaParameter(
				_getName(parameterDefAST));

			DetailAST typeAST = parameterDefAST.findFirstToken(TokenTypes.TYPE);

			if (typeAST.getFirstChild() != null) {
				javaLambdaParameter.setJavaType(_parseJavaType(typeAST));
			}

			javaLambdaParameters.add(javaLambdaParameter);
		}

		return javaLambdaParameters;
	}

	private static JavaExpression _parseJavaMethodCall(
		DetailAST methodCallAST) {

		DetailAST identAST = methodCallAST.findFirstToken(TokenTypes.IDENT);

		if (identAST != null) {
			JavaMethodCall javaMethodCall = new JavaMethodCall(
				identAST.getText());

			javaMethodCall.setGenericJavaTypes(
				_parseGenericJavaTypes(
					methodCallAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
					TokenTypes.TYPE_ARGUMENT));
			javaMethodCall.setParameterValueJavaExpressions(
				_parseParameterValueJavaExpressions(
					methodCallAST.findFirstToken(TokenTypes.ELIST)));

			return javaMethodCall;
		}

		DetailAST dotAST = methodCallAST.findFirstToken(TokenTypes.DOT);

		JavaExpression javaExpression = parseJavaExpression(
			dotAST.getFirstChild());

		DetailAST lastChildAST = dotAST.getLastChild();

		JavaMethodCall javaMethodCall = new JavaMethodCall(
			lastChildAST.getText());

		javaMethodCall.setGenericJavaTypes(
			_parseGenericJavaTypes(
				dotAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));
		javaMethodCall.setParameterValueJavaExpressions(
			_parseParameterValueJavaExpressions(
				methodCallAST.findFirstToken(TokenTypes.ELIST)));

		javaExpression.setChainedJavaExpression(javaMethodCall);

		return javaExpression;
	}

	private static JavaMethodReference _parseJavaMethodReference(
		DetailAST methodRefAST) {

		DetailAST lastChildAST = methodRefAST.getLastChild();

		return new JavaMethodReference(
			lastChildAST.getText(),
			parseJavaExpression(methodRefAST.getFirstChild()));
	}

	private static JavaNewArrayInstantiation _parseJavaNewArrayInstantiation(
		DetailAST literalNewAST) {

		JavaNewArrayInstantiation javaNewArrayInstantiation =
			new JavaNewArrayInstantiation();

		JavaArrayDeclarator javaArrayDeclarator = new JavaArrayDeclarator(
			_getName(literalNewAST));

		javaArrayDeclarator.setDimensionValueJavaExpressions(
			_parseArrayValueJavaExpressions(
				literalNewAST.findFirstToken(TokenTypes.ARRAY_DECLARATOR)));
		javaArrayDeclarator.setGenericJavaTypes(
			_parseGenericJavaTypes(
				literalNewAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));

		javaNewArrayInstantiation.setJavaArrayDeclarator(javaArrayDeclarator);

		DetailAST arrayInitAST = literalNewAST.findFirstToken(
			TokenTypes.ARRAY_INIT);

		if (arrayInitAST != null) {
			javaNewArrayInstantiation.setInitialJavaArray(
				_parseJavaArray(arrayInitAST));
		}

		return javaNewArrayInstantiation;
	}

	private static JavaNewClassInstantiation _parseJavaNewClassInstantiation(
		DetailAST literalNewAST) {

		JavaNewClassInstantiation javaNewClassInstantiation =
			new JavaNewClassInstantiation();

		javaNewClassInstantiation.setJavaClassCall(
			_parseJavaClassCall(literalNewAST));

		return javaNewClassInstantiation;
	}

	private static JavaOperatorExpression _parseJavaOperatorExpression(
		DetailAST detailAST, JavaOperator javaOperator) {

		JavaOperatorExpression javaOperatorExpression =
			new JavaOperatorExpression(javaOperator);

		if (javaOperator.hasLeftHandExpression()) {
			javaOperatorExpression.setLeftHandJavaExpression(
				parseJavaExpression(detailAST.getFirstChild()));
		}

		if (javaOperator.hasRightHandExpression()) {
			javaOperatorExpression.setRightHandJavaExpression(
				parseJavaExpression(detailAST.getLastChild()));
		}

		return javaOperatorExpression;
	}

	private static JavaParameter _parseJavaParameter(
		DetailAST parameterDefAST) {

		JavaParameter javaParameter = new JavaParameter(
			_getName(parameterDefAST));

		DetailAST modifiersAST = parameterDefAST.findFirstToken(
			TokenTypes.MODIFIERS);

		javaParameter.setJavaAnnotations(_parseJavaAnnotations(modifiersAST));
		javaParameter.setModifiers(_parseModifiers(modifiersAST));

		DetailAST typeAST = parameterDefAST.findFirstToken(TokenTypes.TYPE);

		JavaType javaType = _parseJavaType(typeAST);

		DetailAST ellipsisAST = parameterDefAST.findFirstToken(
			TokenTypes.ELLIPSIS);

		if (ellipsisAST != null) {
			javaType.setVarargs(true);
		}

		javaParameter.setJavaType(javaType);

		return javaParameter;
	}

	private static List<JavaParameter> _parseJavaParameters(
		DetailAST parametersAST) {

		List<JavaParameter> javaParameters = new ArrayList<>();

		List<DetailAST> parameterDefASTList = DetailASTUtil.getAllChildTokens(
			parametersAST, false, TokenTypes.PARAMETER_DEF);

		for (DetailAST parameterDefAST : parameterDefASTList) {
			javaParameters.add(_parseJavaParameter(parameterDefAST));
		}

		return javaParameters;
	}

	private static JavaSignature _parseJavaSignature(DetailAST detailAST) {
		DetailAST identAST = detailAST.findFirstToken(TokenTypes.IDENT);

		JavaSignature javaSignature = new JavaSignature(identAST.getText());

		javaSignature.setExceptionJavaExpressions(
			_parseExceptionJavaExpressions(
				detailAST.findFirstToken(TokenTypes.LITERAL_THROWS)));
		javaSignature.setGenericJavaTypes(
			_parseGenericJavaTypes(
				detailAST.findFirstToken(TokenTypes.TYPE_PARAMETERS),
				TokenTypes.TYPE_PARAMETER));

		DetailAST modifiersAST = detailAST.findFirstToken(TokenTypes.MODIFIERS);

		javaSignature.setModifiers(_parseModifiers(modifiersAST));

		javaSignature.setJavaParameters(
			_parseJavaParameters(
				detailAST.findFirstToken(TokenTypes.PARAMETERS)));
		javaSignature.setReturnJavaType(
			_parseJavaType(detailAST.findFirstToken(TokenTypes.TYPE)));

		return javaSignature;
	}

	private static JavaTernaryOperator _parseJavaTernaryOperator(
		DetailAST questionAST) {

		JavaTernaryOperator javaTernaryOperator = new JavaTernaryOperator();

		javaTernaryOperator.setConditionJavaExpression(
			parseJavaExpression(questionAST.getFirstChild()));

		DetailAST colonAST = questionAST.findFirstToken(TokenTypes.COLON);

		javaTernaryOperator.setFalseValueJavaExpression(
			parseJavaExpression(colonAST.getNextSibling()));
		javaTernaryOperator.setTrueValueJavaExpression(
			parseJavaExpression(colonAST.getPreviousSibling()));

		return javaTernaryOperator;
	}

	private static JavaType _parseJavaType(DetailAST detailAST) {
		if (detailAST == null) {
			return null;
		}

		DetailAST childAST = detailAST.getFirstChild();

		int arrayDimension = 0;

		while (childAST.getType() == TokenTypes.ARRAY_DECLARATOR) {
			arrayDimension++;

			childAST = childAST.getFirstChild();
		}

		FullIdent typeIdent = FullIdent.createFullIdent(childAST);

		JavaType javaType = new JavaType(typeIdent.getText(), arrayDimension);

		DetailAST typeInfoAST = childAST;

		if (childAST.getType() != TokenTypes.DOT) {
			typeInfoAST = childAST.getParent();
		}

		javaType.setGenericJavaTypes(
			_parseGenericJavaTypes(
				typeInfoAST.findFirstToken(TokenTypes.TYPE_ARGUMENTS),
				TokenTypes.TYPE_ARGUMENT));

		DetailAST typeLowerBoundsAST = typeInfoAST.findFirstToken(
			TokenTypes.TYPE_LOWER_BOUNDS);

		if (typeLowerBoundsAST != null) {
			javaType.setLowerBoundJavaTypes(
				_parseGenericBoundJavaTypes(typeLowerBoundsAST));
		}

		DetailAST typeUpperBoundsAST = typeInfoAST.findFirstToken(
			TokenTypes.TYPE_UPPER_BOUNDS);

		if (typeUpperBoundsAST != null) {
			javaType.setUpperBoundJavaTypes(
				_parseGenericBoundJavaTypes(typeUpperBoundsAST));
		}

		return javaType;
	}

	private static JavaTypeCast _parseJavaTypeCast(DetailAST typeCastAST) {
		JavaTypeCast javaTypeCast = new JavaTypeCast();

		javaTypeCast.setJavaType(
			_parseJavaType(typeCastAST.findFirstToken(TokenTypes.TYPE)));
		javaTypeCast.setValueJavaExpression(
			parseJavaExpression(typeCastAST.getLastChild()));

		return javaTypeCast;
	}

	private static List<JavaSimpleValue> _parseModifiers(
		DetailAST modifiersAST) {

		List<JavaSimpleValue> modifiers = new ArrayList<>();

		DetailAST childAST = modifiersAST.getFirstChild();

		while (true) {
			if (childAST == null) {
				return modifiers;
			}

			if (childAST.getType() != TokenTypes.ANNOTATION) {
				modifiers.add(new JavaSimpleValue(childAST.getText()));
			}

			childAST = childAST.getNextSibling();
		}
	}

	private static List<JavaExpression> _parseParameterValueJavaExpressions(
		DetailAST elistAST) {

		List<JavaExpression> parameterValueJavaExpressions = new ArrayList<>();

		DetailAST childAST = elistAST.getFirstChild();

		if (childAST == null) {
			return parameterValueJavaExpressions;
		}

		while (true) {
			parameterValueJavaExpressions.add(parseJavaExpression(childAST));

			childAST = childAST.getNextSibling();

			if (childAST == null) {
				return parameterValueJavaExpressions;
			}

			childAST = childAST.getNextSibling();
		}
	}

	private static final int[] _SIMPLE_TYPES = {
		TokenTypes.CHAR_LITERAL, TokenTypes.IDENT, TokenTypes.LITERAL_BOOLEAN,
		TokenTypes.LITERAL_BYTE, TokenTypes.LITERAL_CHAR,
		TokenTypes.LITERAL_CLASS, TokenTypes.LITERAL_DOUBLE,
		TokenTypes.LITERAL_FALSE, TokenTypes.LITERAL_FLOAT,
		TokenTypes.LITERAL_INT, TokenTypes.LITERAL_LONG,
		TokenTypes.LITERAL_NULL, TokenTypes.LITERAL_SHORT,
		TokenTypes.LITERAL_SUPER, TokenTypes.LITERAL_TRUE,
		TokenTypes.LITERAL_THIS, TokenTypes.LITERAL_VOID, TokenTypes.NUM_DOUBLE,
		TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT, TokenTypes.NUM_LONG,
		TokenTypes.STRING_LITERAL
	};

}