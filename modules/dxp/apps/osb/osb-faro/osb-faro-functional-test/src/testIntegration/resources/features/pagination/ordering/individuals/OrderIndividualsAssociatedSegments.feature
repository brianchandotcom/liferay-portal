@spira_Order @Order @Individuals @List @team_FARO @priority_3
Feature: Order an Individual's Associated Segments List
	As an Business User, I should be able to order an Individual's Associated Segments List

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Order an Individual's Associated Segments List
		Given I search for "Tory Glover"
		And I click "Tory Glover" in the table
		And I click the "Segments" tab

		# Order by Name

		When I sort the table by the "Name" column header in descending order
		Then I should see the segments sorted in descending alphabetical order
		When I sort the table by the "Name" column header in ascending order
		Then I should see the segments sorted in ascending alphabetical order

		# Order by Membership Count

		When I sort the table by the "Membership" column header in descending order
		Then I should see the following ordered rows in column 1:
			| everybody |
			| engineers |
		When I sort the table by the "Membership" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| engineers |
			| everybody |
