@spira_Data_Source @Data_Source @Data_Transformation @team_FARO @priority_5 @blocked
Feature: Perform Data Transformations
	As a Business User, I should be able to upload a CSV Data Source and perform data transformations on its fields

	Background: [Setup] Upload a CSV Data Source and reach the Data Transformation page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "DataTransformation - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle,${Random.2}
			Opal.Henderson@gmail.com,Opal,Henderson,Economist,${Random.3}
			Nettie.Morgan@gmail.com,Nettie,Morgan,Civil Engineer,${Random.4}
			"""
		* I click the "CSV File" button
		* I browse for a file named "DataTransformation - ${Random.1}.csv"
		* I name the Data Source "AddCSVDataSource - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "1" fields not mapped

	Scenario: Add a Field
		When I click the "Add Field" button
		Then I should see "6" rows of data model matches

	Scenario: Delete a Field
		When I delete the data model match in row "2"
		Then I should see "4" rows of data model matches