@spira_Order @Order @Accounts @List @team_FARO @priority_3
Feature: Order an Account's Associated Segments
	As a Business User, I should be able order different columns of an Account's Associated Segments list

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Annis Osinski"
		* I click the checkbox on the table row containing "Annis Osinski"
		* I click the "Add" button
		* I name the Static segment "OrderAccountAssociatedSegments Segment" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Austin Ruecker"
		* I click the checkbox on the table row containing "Austin Ruecker"
		* I search for "Barney Gusikowski"
		* I click the checkbox on the table row containing "Barney Gusikowski"
		* I click the "Add" button
		* I name the Static segment "Two Member Segment" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Annis Osinski"
		* I click the checkbox on the table row containing "Annis Osinski"
		* I search for "Sharla Witting"
		* I click the checkbox on the table row containing "Sharla Witting"
		* I search for "Lavina Waelchi"
		* I click the checkbox on the table row containing "Lavina Waelchi"
		* I click the "Add" button
		* I name the Static segment "A Segment" and save it
		* I go to the "Accounts" page
		* I search for "Schneider and Sons"
		* I click "Schneider and Sons" in the table
		* I click the "Segments" tab

	Scenario: Order Associated Segments List

		# Order by segment

		When I click the "Order" button
		And I click the "Name" dropdown option
		Then I should see the following ordered rows in column 1:
			| A Segment                              |
			| engineers                              |
			| everybody                              |
			| OrderAccountAssociatedSegments Segment |
			| Two Member Segment                     |

		# Order by segment members

		When I click the "Members" dropdown option
		Then I should see the following ordered rows in column 2:
			| 98 |
			| 3   |
			| 3   |
			| 2   |
			| 1   |
