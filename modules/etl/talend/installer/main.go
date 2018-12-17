package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"
	"strings"

	"installer/constants"
	"installer/utils/fileutils"
	"installer/verifier"
)

func displayInitializationMessages() {
	const decorLine = "\t\t#################################################" +
		"######################"

	fmt.Println("\n" + decorLine)
	fmt.Println(
		"\t\t### Liferay component family installer for Talend Open Studio " +
			constants.SUPPORTED_TOS_VERSION + " ###")
	fmt.Println(decorLine + "\n")

	fmt.Printf("Installer's location: \t\t\t%s\n",
		constants.GetBinaryLocation())
	fmt.Printf("Talend Open Studio's Home directory: \t%s\n",
		constants.TosHomeDir)
}

func main() {
	displayInitializationMessages()

	verifier.VerifyInstallerIntegrity()
	verifier.VerifyTOSLocation()
	verifier.VerifyTOSVersion()

	// Copy the component artifacts
	copyTasks := []fileutils.CopyTask{
		{filepath.Join(
			constants.ThirdPartyDependenciesDir, ".m2"), constants.TosM2Dir},
		{filepath.Join(
			constants.ComponentArtifactsDir, ".m2"), constants.TosM2Dir},
		{fileutils.GetSrcComponentDefinitionPath(),
			filepath.Join(
				constants.TosHomeDir, "plugins",
				fileutils.GetSrcComponentDefinitionName())},
	}

	for _, copyTask := range copyTasks {
		err := copyTask.Execute()

		if err != nil {
			log.Printf("Unable to copy files for the component: %v", err)
			log.Fatal("Installation failed")
		}
	}

	// Delete the TOS Eclipse OSGi / temp files
	files, err := ioutil.ReadDir(constants.TosM2HomeDir)
	if err != nil || len(files) == 0 {
		log.Printf("Unable to read Talend's configuration folder: %v", err)
	}

	for _, file := range files {
		if file.IsDir() && strings.HasPrefix(file.Name(), "org.eclipse.") {
			os.RemoveAll(filepath.Join(constants.TosM2HomeDir, file.Name()))
		}
	}

	fmt.Println("\nInstallation of the Liferay component was successful!")
}
