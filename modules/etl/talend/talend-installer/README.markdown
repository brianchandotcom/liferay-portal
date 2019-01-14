# Liferay Component installer for Talend Open Studio

This project contains the source code for the cross platform compatible
installer for Talend Open Studio (TOS) written in Go.

## Prerequisites

* JDK 1.8+
* [Apache Maven 3.3+](https://maven.apache.org/)
* [Go 1.11.2](https://golang.org/)
* [Gox - Simple Go Cross Compilation](https://github.com/mitchellh/gox)

## Packaging for distribution

1. Run the `packageInstaller` task in the `build.gradle` script which can be
	found in the [talend-installer](https://github.com/liferay/liferay-portal/tree/master/modules/etl/talend/talend-installer)
	directory
2. It compiles and creates the installer for different OSes and archives
	it in Zip format, finally places it in the following directory:
	`talend-installer/build/dist/` named as `tLiferayComponents-TOS-${version}-yyyyMMddHHmmssSSS.zip`

## Manual Build

1. Navigate to the [talend-installer](https://github.com/liferay/liferay-portal/tree/master/modules/etl/talend/talend-installer)
2. Build the `installer` (Go module) with Gox to have cross platform binaries
	* `gox -osarch="darwin/amd64 linux/amd64 windows/amd64" -output $GOPATH"bin/talend-installer/{{.Dir}}-{{.OS}}-{{.Arch}}" talend-installer`
	* The tool above creates a new folder in your workspace `$GOPATH/bin` folder named `talend-installer`
	* The installer binaries for Windows / Mac / Linux (64 bit) will be located in the `talend-installer` directory:
	```
		installer-darwin-amd64 -------> For macOS
		installer-linux-amd64  -------> Linux
		installer-windows-amd64.exe --> Windows
	```

## Running the installer

To run the installer the appropriate OS specific executable binary should be run
with a `-tos` flag where you can specify the Talend Open Studio's home folder

The TOS home folder is located where you extracted the TOS archive
_(TOS_ESB-${timestamp}-${version}.zip)_ and there you can find the `Studio/` named
folder.

For example, you can run the installer on Linux 64 bit with the following
command

```
$ cd /usr/talend-installer
$ ./installer-linux-amd64 -tos=/usr/workspace/TOS_ESB-${timestamp}-${version}/Studio
```