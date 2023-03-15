@spira_Order @Order @Segments @List @team_FARO @priority_4
Feature: Order the Segment List
	As an Business User, I should be able to sort the Segments List

	Background: [Setup] Create a Static Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Ben"
		* I click the checkbox on the table row containing "Ben Larkin"
		* I search for "Annis"
		* I click the checkbox on the table row containing "Annis Osinski"
		* I search for "Alyse"
		* I click the checkbox on the table row containing "Alyse Cronin"
		* I click the "Add" button
		* I name the Static segment "AssertSegmentListKebab Segment" and save it
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Alena Will"
		* I click the checkbox on the table row containing "Alena Will"
		* I click the "Add" button
		* I name the Static segment "z AssertSegmentListKebab Segment" and save it

	Scenario: Sort the Segments List
		Given I go to the "Segments" page

		# Order by Segment Name

		When I sort the table by the "Name" column header in ascending order
		Then I should see the users sorted in ascending alphabetical order
		When I sort the table by the "Name" column header in descending order
		Then I should see the users sorted in descending alphabetical order

		# Order by Member count

		When I sort the table by the "Membership" column header in descending order
		Then I should see the following ordered rows in column 1:
			| everybody                        |
			| engineers                        |
			| AssertSegmentListKebab Segment   |
			| z AssertSegmentListKebab Segment |
		When I sort the table by the "Membership" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| z AssertSegmentListKebab Segment |
			| engineers                        |
			| AssertSegmentListKebab Segment   |
			| everybody                        |

		# Order by Total Activities

		When I sort the table by the "Total Activities" column header in descending order
		Then I should see the following ordered rows in column 1:
			| everybody                        |
			| engineers                        |
			| AssertSegmentListKebab Segment   |
			| z AssertSegmentListKebab Segment |
		When I sort the table by the "Total Activities" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| AssertSegmentListKebab Segment   |
			| z AssertSegmentListKebab Segment |
			| engineers                        |
			| everybody                        |

		# Order by 30 Day Engagement

		When I sort the table by the "30 Day Engagement" column header in descending order
		Then I should see the following ordered rows in column 1:
			| everybody                        |
			| engineers                        |
			| AssertSegmentListKebab Segment   |
			| z AssertSegmentListKebab Segment |
		When I sort the table by the "30 Day Engagement" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| AssertSegmentListKebab Segment   |
			| z AssertSegmentListKebab Segment |
			| engineers                        |
			| everybody                        |