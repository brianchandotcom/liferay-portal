@spira_Data_Source @Data_Source @Deletion @team_FARO @priority_4 @blocked
Feature: Assert Data Source Deletion Status
	As an Business User, I should see the status of my in progress Data Source deletion

	Background: [Setup] Navigate to the Data Source Page and Add a DXP Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Data Source" page
		* I click the "Add Data Source" button
		* I create a file named "AssertDataSourceDeletionStatus - ${Random.1}.csv" with the following content:
			"""
			email,givenName,familyName,jobTitle
			1${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			2${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			3${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			4${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			5${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			6${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			7${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			8${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			9${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			10${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			11${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			12${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			13${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			14${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			15${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			16${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			17${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			18${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			19${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			20${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			21${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			22${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			23${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			24${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			25${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			26${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			27${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			28${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			29${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			30${Random.3}@gmail.com,first${Random.3},last${Random.3},Civil Engineer
			"""
		* I click the "CSV File" button
		* I browse for a file named "AssertDataSourceDeletionStatus - ${Random.1}.csv"
		* I name the Data Source "AssertDataSourceDeletionStatus - ${Random.1}"
		* I click the "Next" button
		* I should see "4" fields mapped and "0" fields not mapped
		* I click the "Done" button
		* I should see that "AssertDataSourceDeletionStatus - ${Random.1}" was successfully uploaded
	
	Scenario: Assert Data Source Deletion Status
		Given I go to the "Data Source" page
		And I click "AssertDataSourceDeletionStatus - ${Random.1}" in the table
		When I click the "Delete Data Source" button
		And I type "Remove AssertDataSourceDeletionStatus - ${Random.1}" into the delete input field
		And I click the "Delete Data Source" button
		And I click the "Delete Data Source" button
		Then I should see an info alert saying "Info:AssertDataSourceDeletionStatus - ${Random.1} is currently being removed from Analytics Cloud."
		And I should see that the "AssertDataSourceDeletionStatus - ${Random.1}" data source status is currently "Deletion in Progress"
