package com.liferay.portal.store.azure.internal;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author Josef Sustacek
 */
public class FullPathsMapperTest {

	@BeforeClass
	public static void setUp() {
		_sut = new FullPathsMapper();
	}

	@Test
	public void toAzureBlobName() {
		assertEquals(
			"20000/10000/file 1.pdf/1.0",
			_sut.toAzureBlobName(20000, 10000, "file 1.pdf", "1.0"));

		assertEquals(
			"20000/10000/sub dir/file-1.pdf/1.1",
			_sut.toAzureBlobName(20000, 10000, "sub dir/file-1.pdf", "1.1"));

		assertEquals(
			"20000/10000/file-1.pdf/1.1",
			_sut.toAzureBlobName(20000, 10000, "/file-1.pdf/", "1.1"));

		// just in case
		assertEquals(
			"20000/10000/file-1.pdf",
			_sut.toAzureBlobName(20000, 10000, "file-1.pdf", ""));

		// just in case
		assertEquals(
			"20000/10000/1.0",
			_sut.toAzureBlobName(20000, 10000, "", "1.0"));
	}

	@Test(expected = NullPointerException.class)
	public void toAzureBlobName_invalid1() {
		_sut.toAzureBlobName(20000, 10000, null, "1.0");
	}

	@Test(expected = NullPointerException.class)
	public void toAzureBlobName_invalid2() {
		_sut.toAzureBlobName(20000, 10000, "file.pdf", null);
	}

	@Test
	public void toAzureBlobsPrefix() {
		assertEquals(
			"30000/10001/file 1.pdf/",
			_sut.toAzureBlobPrefix(30000, 10001, "file 1.pdf"));

		assertEquals(
			"30000/10001/dir/sub dir/",
			_sut.toAzureBlobPrefix(30000, 10001, "/dir/sub dir/"));

		assertEquals(
			"30000/10001/",
			_sut.toAzureBlobPrefix(30000, 10001, ""));

		// just in case
		assertEquals(
			"30000/10001/",
			_sut.toAzureBlobPrefix(30000, 10001, "/"));

	}

	@Test(expected = NullPointerException.class)
	public void toAzureBlobsPrefix_invalid1() {
		_sut.toAzureBlobPrefix(30000, 10001, null);
	}

	@Test
	public void toLiferayFileName() {
		// most likely, with space in file name
		assertEquals(
			"file 1.pdf",
			_sut.toLiferayFileName(40000, 10002, "40000/10002/file 1.pdf/1.0"));

		// no file extension
		assertEquals(
			"file 1",
			_sut.toLiferayFileName(40000, 10002, "40000/10002/file 1/1.0"));

		// subdir
		assertEquals(
			"subdir/file 1.pdf",
			_sut.toLiferayFileName(40000, 10002, "40000/10002/subdir/file 1.pdf/1.0"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayFileName_invalid1_wrongCompany() {
		_sut.toLiferayFileName(50000, 10002, "40000/10002/file-1.pdf/1.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayFileName_invalid2_wrongRepo() {
		_sut.toLiferayFileName(40000, 10009, "40000/10002/file-1.pdf/1.0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayFileName_invalid3_noVersion() {
		_sut.toLiferayFileName(40000, 10002, "40000/10002/file-1.pdf");
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayFileName_invalid4_noPath() {
		_sut.toLiferayFileName(40000, 10002, "40000/10002");
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayFileName_invalid5_noPath() {
		_sut.toLiferayFileName(40000, 10002, "40000/10002/");
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayFileName_invalid5_noVersion() {
		_sut.toLiferayFileName(40000, 10002, "40000/10002/f");
	}
	
	@Test
	public void toLiferayDirName() {
		// most likely, with space in file name
		assertEquals(
			"dir",
			_sut.toLiferayDirName(40000, 10002, "40000/10002/dir/"));

		assertEquals(
			"dir/subdir",
			_sut.toLiferayDirName(40000, 10002, "40000/10002/dir/subdir/"));

		assertEquals(
			"dir/file 1.pdf",
			_sut.toLiferayDirName(40000, 10002, "40000/10002/dir/file 1.pdf/"));

		assertEquals(
			"",
			_sut.toLiferayDirName(40000, 10002, "40000/10002/"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayDirName_invalid1_missingDelimiter() {
		_sut.toLiferayDirName(40000, 10002, "40000/10002/dir/subdir");
	}

	@Test(expected = IllegalArgumentException.class)
	public void toLiferayDirName_invalid2_missingDelimiterWithRoot() {
		_sut.toLiferayDirName(40000, 10002, "40000/10002");
	}

	@Test
	public void toFullAzurePath() {
		assertEquals(
			"40000/10002/dir/subdir",
			_sut.toFullAzurePath(40000, 10002, "dir/subdir", Optional.empty()));

		assertEquals(
			"40000/10002/dir/file 1.pdf",
			_sut.toFullAzurePath(40000, 10002, "dir/file 1.pdf", Optional.empty()));

		assertEquals(
			"40000/10002/dir/file 1.pdf/1.1",
			_sut.toFullAzurePath(40000, 10002, "dir/file 1.pdf", Optional.of("1.1")));

		assertEquals(
			"40000/10002/dir/file 1.pdf",
			_sut.toFullAzurePath(40000, 10002, "/dir/file 1.pdf", Optional.empty()));

		assertEquals(
			"40000/10002/dir/file 1.pdf",
			_sut.toFullAzurePath(40000, 10002, "dir/file 1.pdf/", Optional.empty()));

		assertEquals(
			"40000/10002/dir/file 1.pdf/1.2",
			_sut.toFullAzurePath(40000, 10002, "dir/file 1.pdf/", Optional.of("1.2")));

		assertEquals(
			"40000/10002/dir/file 1.pdf",
			_sut.toFullAzurePath(40000, 10002, "/dir/file 1.pdf/", Optional.empty()));

		assertEquals(
			"40000/10002",
			_sut.toFullAzurePath(40000, 10002, "", Optional.empty()));

		assertEquals(
			"40000/10002",
			_sut.toFullAzurePath(40000, 10002, "/", Optional.empty()));
	}

	private static FullPathsMapper _sut;
}