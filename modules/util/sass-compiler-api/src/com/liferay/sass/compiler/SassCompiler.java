package com.liferay.sass.compiler;

/**
 * @author David Truong
 */
public interface SassCompiler {
	public String compileFile(
			String inputFileName, String includeDirName, String imgDirName)
		throws SassCompilerException;
	public String compileString(
		String input, String includeDirName, String imgDirName)
		throws SassCompilerException;
}