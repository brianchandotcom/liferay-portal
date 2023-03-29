@spira_Data_Source @Data_Source @CSV @team_FARO @priority_5 @blocked
Feature: Cancel After Uploading CSV File
	As an Business User, I should be able to cancel after uploading a CSV data source

	Background: [Setup] Navigate to Data Source Upload Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "CancelAfterUploadingCSV - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			Opal.Henderson@gmail.com,Opal,Henderson,Economist
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer
			"""
		* I click the "CSV File" button

	Scenario: Cancel After Uploading CSV File
		Given I browse for a file named "CancelAfterUploadingCSV - ${Random.1}.csv"
		When I click the "Cancel" button
		And I go to the "Data Source" page
		And I search for "CancelAfterUploadingCSV - ${Random.1}.csv"
		Then I should not see a "CSV" Data Source named "CancelAfterUploadingCSV - ${Random.1}.csv"