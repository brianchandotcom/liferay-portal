package com.liferay.sass.compiler;

/**
 * @author David Truong
 */
public class SassCompilerException extends Exception {
	public SassCompilerException() {
	}

	public SassCompilerException(String message) {
		super(message);
	}

	public SassCompilerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SassCompilerException(Throwable throwable) {
		super(throwable);
	}

}