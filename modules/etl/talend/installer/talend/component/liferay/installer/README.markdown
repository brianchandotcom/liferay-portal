# Liferay Component installer for Talend Open Studio

This project contains the source code for the cross platform compatible
installer for Talend Open Studio (TOS) written in Go.


## Prerequisites

* [Go 1.11](https://golang.org/)
* [Gox - Simple Go Cross Compilation](https://github.com/mitchellh/gox)
* Required resources for the installer
	* [3rd party dependencies](https://github.com/ZoltanTakacs/golang/tree/master/talend/component/liferay/installer/_resources): (`3rd-party-dependencies`)
	* [Component artifact files](#building-the-component-artifacts) (`component-artifacts` and `component-definition`)

### Building the component artifacts

1. Setup the environment for [Talend Components for Liferay](https://github.com/liferay/liferay-portal/tree/b27557dcf7e63a59f6ad07cf029a002d0b56efba/modules/etl/talend)
2. [Build the component family](https://github.com/liferay/liferay-portal/tree/b27557dcf7e63a59f6ad07cf029a002d0b56efba/modules/etl/talend#build)
	* A directory named `tos-dependencies` will be created after the `install` lifecycle in the project root folder
		* The folder `tos-dependencies` contains two sub folders:
		* (1) `component-artifacts` and (2) `component-definition`


## Build

1. Clone the [installer's repository](https://github.com/ZoltanTakacs/golang) into your [Go workspace](https://golang.org/doc/code.html#Workspaces)
2. Build the installer with Gox to have cross platform binaries
	* `gox -osarch="darwin/amd64 linux/amd64 windows/amd64" -output $GOPATH"bin/talend-installer/{{.Dir}}-{{.OS}}-{{.Arch}}" github.com/ZoltanTakacs/golang/talend/component/liferay/installer`
	* The tool above creates a new folder in your workspace `$GOPATH/bin` folder named `talend-installer`
	* The installer binaries for Windows / Mac / Linux (64 bit) will be located in the `talend-installer` directory: 
	```
		installer-darwin-amd64 -------> For macOS
		installer-linux-amd64  -------> Linux
		installer-windows-amd64.exe --> Windows
	```

## Packaging for distribution

1. Copy the folders determined in `Required resources for the installer` section into the `talend-installer` directory
	* Directory structure should look like this:
	```
	talend-installer\
		3rd-party-dependencies\
		component-artifacts\
		component-definition\
		installer-darwin-amd64
		installer-linux-amd64
		installer-windows-amd64.exe
	```
2. Archive them into a package ([7zip](https://www.7-zip.org/), zip, etc...)
	* Note: Folder structure matters
	
## Run

To run the installer the appropriate OS specific executable binary should be run
with a `-tos` flag where you can specify the Talend Open Studio's home folder

The TOS home folder is located where you extracted the TOS archive
_(TOS_ESB-20180411_1414-V7.0.1.zip)_ and there you can find the `Studio/` named
folder.

For example, you can run the installer on Linux 64 bit with the following
command 

```
$ cd /usr/talend-installer
$ ./installer-linux-amd64 -tos=/usr/workspace/TOS_ESB-20180411_1414-V7.0.1/Studio
```